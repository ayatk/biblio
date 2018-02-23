package com.ayatk.biblio.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ayatk.biblio.data.entity.ChapterEntity
import io.reactivex.Flowable

@Dao
interface ChapterDao {

  @Query("SELECT * FROM chapter WHERE novel_code = :code")
  fun getAllChapterByCode(code: String): Flowable<List<ChapterEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(chapter: ChapterEntity)

  @Delete
  fun delete(chapter: ChapterEntity)
}
