package com.example.aacstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private class MainLiveData:LiveData<String>(){
        override fun onActive() {
            super.onActive()
            println("onActive: ${hasActiveObservers()}")
        }

        override fun onInactive() {
            super.onInactive()
            println("onInactive: ${hasActiveObservers()}")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("onCreate: ${lifecycle.currentState.name}")

        val lifecycleObservable = object :LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate(source: LifecycleOwner){
                println("OnLifecycleEvent_onCreate")
                println( "ON_CREATE: ${source.lifecycle.currentState.name}")
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
            fun onAny(source:LifecycleOwner,event:Lifecycle.Event){
                println( "ON_ANY: ${source.lifecycle.currentState.name}")
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop(source:LifecycleOwner){
                source.lifecycle.removeObserver(this)
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(source: LifecycleOwner){
                println( "ON_Destroy: ${source.lifecycle.currentState.name}")
            }
        }
        lifecycle.addObserver(lifecycleObservable)
        supportFragmentManager.beginTransaction().add(MainFragment(),"MainFragment").commit()

        val viewModel1 = ViewModelProviders.of(this).get(MainViewMoodel::class.java)
//        val viewModel2 = ViewModelProviders.of(this).get(MainViewMoodel::class.java)
        val viewModel4 = ViewModelProviders.of(this).get("1",MainViewMoodel::class.java)
        val viewModel5 = ViewModelProviders.of(this).get("2",MainViewMoodel::class.java)
        println("${viewModel4} ${viewModel5}")
        println("${viewModel1}")

//        val liveData = MainLiveData()
//        toggleButton.setOnCheckedChangeListener { _, isChecked ->
//            if(isChecked){
//                liveData.observe(this, Observer {
//                    println("${it}")
//                })
//            } else {
//                liveData.removeObservers(this)
//            }
//        }
//        Transformations.map(
//            CountUpLiveData()
//        ){
//            it * it
//        }.observe(this, Observer {
//            println(it)
//        })
//        liveData.observe(this, Observer {
//            println("livedata: ${it}")
//        })
//        button.setOnClickListener {
//            liveData.postValue("a")
//            liveData.postValue("b")
//        }
    }

    override fun onStart() {
        super.onStart()
        println("onStart: ${lifecycle.currentState.name}")
    }

    override fun onResume() {
        super.onResume()
        println("onResume: ${lifecycle.currentState.name}")
    }
    override fun onPause() {
        super.onPause()
        println("onPause: ${lifecycle.currentState.name}")
    }

    override fun onStop() {
        super.onStop()
        println("onStop: ${lifecycle.currentState.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy: ${lifecycle.currentState.name}")
    }

    override fun onRestart() {
        super.onRestart()
        println("onCreate: ${lifecycle.currentState.name}")
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        println("onSaveInstanceState: ${lifecycle.currentState.name}")
    }
}
