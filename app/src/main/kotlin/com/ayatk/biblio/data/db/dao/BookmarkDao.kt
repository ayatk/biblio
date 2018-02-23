package com.ayatk.biblio.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ayatk.biblio.data.entity.BookmarkEntity
import io.reactivex.Flowable

@Dao
interface BookmarkDao {

  @Query("SELECT * FROM bookmark")
  fun getAllBookmark(): Flowable<List<BookmarkEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(bookmark: BookmarkEntity)

  @Delete
  fun delete(bookmark: BookmarkEntity)
}
