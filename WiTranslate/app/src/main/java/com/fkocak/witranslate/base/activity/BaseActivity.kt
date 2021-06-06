package com.fkocak.witranslate.base.activity


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.viewbinding.ViewBinding
import com.fkocak.witranslate.base.vm.BaseVM
import com.google.android.material.snackbar.Snackbar
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import timber.log.Timber


abstract class BaseActivity<VB : ViewBinding>(open val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    lateinit var pb: ProgressBar

    lateinit var binding: VB

    private val context: Context
        get() = this

    //private var hud_loading: KProgressHUD? = null

    @SuppressLint("SourceLockedOrientationActivity")
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(getLayoutID())

        binding = bindingFactory(layoutInflater)
        createPB()
        setContentView(binding.root)

        initChangeFont()
        initReq()
        initVMListener()
        initChangeUI()

    }

    private fun createPB() {
        val cs = ConstraintSet()
        pb = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        pb.id = View.generateViewId()
        pb.isIndeterminate = true
        pb.visibility = View.VISIBLE
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        (binding.root as ConstraintLayout).addView(pb)

        cs.clone((binding.root as ConstraintLayout))
        cs.connect(pb.id, ConstraintSet.TOP, (binding.root as ConstraintLayout).id, ConstraintSet.TOP)
        cs.connect(pb.id, ConstraintSet.START, (binding.root as ConstraintLayout).id, ConstraintSet.START)
        cs.connect(pb.id, ConstraintSet.END, (binding.root as ConstraintLayout).id, ConstraintSet.END)
        cs.connect(pb.id, ConstraintSet.BOTTOM, (binding.root as ConstraintLayout).id, ConstraintSet.BOTTOM)
        cs.applyTo((binding.root as ConstraintLayout))

        // TO DO CHANGE AFTER..!
    }

    abstract fun initChangeFont()

    abstract fun initReq()

    abstract fun initVMListener()

    abstract fun initChangeUI()


    fun prepareWithBaseVM(baseVm: BaseVM) {
        baseVm.loadingHUD.observe(this, {
            if (it) showLoadingHUD() else hideLoadingHUD()
        })

        baseVm.checkForErrWarMes.observe(this, {
            checkForErrWarMes(it)
        })
    }

    private fun showLoadingHUD() {
        pb.visibility = View.VISIBLE
    }

    private fun hideLoadingHUD() {
        if (pb.visibility == View.VISIBLE)
            pb.visibility = View.GONE
    }

    fun checkForErrWarMes(errorMessage: String){
        toast(errorMessage)
    }

    @SuppressLint("WrongConstant")
    protected fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        Timber.i("ERRORR... $message")
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

}
