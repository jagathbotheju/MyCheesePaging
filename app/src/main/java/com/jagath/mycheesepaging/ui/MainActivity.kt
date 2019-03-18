package com.jagath.mycheesepaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jagath.mycheesepaging.R
import com.jagath.mycheesepaging.vm.CheeseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Shows a list of Cheeses, with swipe-to-delete, and an input field at the top to add.
 * <p>
 * Cheeses are stored in a database, so swipes and additions edit the database directly, and the UI
 * is updated automatically using paging components.
 */

class MainActivity : AppCompatActivity() {

    private val cheeseViewModel:CheeseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter=CheeseAdapter()
        cheeseList.adapter=adapter

        cheeseViewModel.populateDB()

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        cheeseViewModel.allCheese.observe(this, Observer(adapter::submitList))

        initAddButtonListener()
        initSwipToDelete()
    }

    private fun initSwipToDelete(){
        ItemTouchHelper(object :ItemTouchHelper.Callback(){
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                    makeMovementFlags(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean =false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseAdapter.CheeseViewHolder).cheese?.let {
                    cheeseViewModel.remove(it)
                }
            }
        }).attachToRecyclerView(cheeseList)
    }

    private fun initAddButtonListener(){
        addButton.setOnClickListener{addCheese()}

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        inputText.setOnEditorActionListener{_,actionId,_->
            if(actionId==EditorInfo.IME_ACTION_DONE){
                addCheese()
                return@setOnEditorActionListener true
            }
            false
        }

        // When the user clicks on the button, or presses enter, save the item.
        inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }
    }



    private fun addCheese(){
        val newCheese=inputText.text.trim()
        if(newCheese.isNotEmpty()){
            cheeseViewModel.insert(newCheese)
            inputText.setText("")
        }
    }
}
