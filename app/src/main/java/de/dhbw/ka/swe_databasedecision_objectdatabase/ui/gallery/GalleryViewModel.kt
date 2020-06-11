package de.dhbw.ka.swe_databasedecision_objectdatabase.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.dhbw.ka.swe_databasedecision_objectdatabase.data.Gegenstand
import de.dhbw.ka.swe_databasedecision_objectdatabase.database.DataAccess
import io.objectbox.Box
import io.objectbox.kotlin.boxFor

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    var gegenstaende: Box<Gegenstand> = DataAccess.boxStore.boxFor()
}