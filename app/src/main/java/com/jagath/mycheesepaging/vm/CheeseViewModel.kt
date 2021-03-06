package com.jagath.mycheesepaging.vm

import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.toLiveData
import com.jagath.mycheesepaging.db.Cheese
import com.jagath.mycheesepaging.repository.CheeseRepository

class CheeseViewModel(val cheeseRepository: CheeseRepository):ViewModel() {

    fun populateDB()=cheeseRepository.populateDB()

    /**
     * We use -ktx Kotlin extension functions here, otherwise you would use LivePagedListBuilder(),
     * and PagedList.Config.Builder()
     */
    val allCheese=cheeseRepository.getAllCheese()
        .toLiveData(Config (
            /**
             * A good page size is a value that fills at least a screen worth of content on a large
             * device so the User is unlikely to see a null item.
             * You can play with this constant to observe the paging behavior.
             * <p>
             * It's possible to vary this with list device size, but often unnecessary, unless a
             * user scrolling on a large device is expected to scroll through items more quickly
             * than a small device, such as when the large device uses a grid layout of items.
             */
            pageSize=30,
            /**
             * If placeholders are enabled, PagedList will report the full size but some items might
             * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
             * <p>
             * If placeholders are disabled, onBind will never receive null but as more pages are
             * loaded, the scrollbars will jitter as new pages are loaded. You should probably
             * disable scrollbars if you disable placeholders.
             */
            enablePlaceholders=true,
            /**
             * Maximum number of items a PagedList should hold in memory at once.
             * <p>
             * This number triggers the PagedList to start dropping distant pages as more are loaded.
             */
            maxSize=200))

    fun insert(text:CharSequence)=cheeseRepository.insert(text)

    fun remove(cheese: Cheese)=cheeseRepository.remove(cheese)
}