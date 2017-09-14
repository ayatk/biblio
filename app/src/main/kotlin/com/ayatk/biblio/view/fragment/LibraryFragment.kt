/*
 * Copyright (c) 2016-2017 Aya Tokikaze. All Rights Reserved.
 */

package com.ayatk.biblio.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.ayatk.biblio.R
import com.ayatk.biblio.databinding.FragmentLibraryBinding
import com.ayatk.biblio.databinding.ViewLibraryItemBinding
import com.ayatk.biblio.view.customview.BindingHolder
import com.ayatk.biblio.view.customview.ObservableListRecyclerAdapter
import com.ayatk.biblio.viewmodel.LibraryItemViewModel
import com.ayatk.biblio.viewmodel.LibraryViewModel
import javax.inject.Inject

class LibraryFragment : BaseFragment() {

  lateinit var binding: FragmentLibraryBinding

  @Inject
  lateinit var viewModel: LibraryViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    binding = FragmentLibraryBinding.inflate(inflater, container, false)
    binding.viewModel = viewModel

    // 色設定
    binding.refresh.setColorSchemeResources(R.color.app_blue)

    initRecyclerView()

    return binding.root
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    component().inject(this)
  }

  override fun onResume() {
    super.onResume()
    viewModel.start(context, false)
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.destroy()
  }

  // TODO: 2017/03/24 一時的なものなので今後消す
  private fun createNovelAddDialog() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val editView = EditText(context)
    editView.hint = "小説のURLを入力"

    val dialog = AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.add_novels))
        .setView(editView)
        .setPositiveButton("OK") { _, _ ->
          viewModel.getNovels(context, editView.text.toString())
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .setNegativeButton("キャンセル") { _, _ ->
          imm.hideSoftInputFromWindow(editView.windowToken, 0)
        }
        .create()

    dialog.show()
    editView.requestFocus()

    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
  }

  private fun initRecyclerView() {
    binding.recyclerView.apply {
      adapter = LibraryAdapter(context, viewModel.libraryViewModels)
      setHasFixedSize(true)
      addItemDecoration(DividerItemDecoration(context, 1))
      layoutManager = LinearLayoutManager(context)
    }
  }

  companion object {
    fun newInstance(): LibraryFragment = LibraryFragment()
  }

  private inner class LibraryAdapter constructor(
      context: Context, list: ObservableList<LibraryItemViewModel>) :
      ObservableListRecyclerAdapter<LibraryItemViewModel, BindingHolder<ViewLibraryItemBinding>>(
          context, list) {

    init {
      setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): BindingHolder<ViewLibraryItemBinding> {
      return BindingHolder(context, parent, R.layout.view_library_item)
    }

    override fun onBindViewHolder(holder: BindingHolder<ViewLibraryItemBinding>, position: Int) {
      holder.binding.apply {
        viewModel = getItem(position)
        executePendingBindings()
      }
    }

    override fun getItemId(position: Int): Long {
      return getItem(position).library.id
    }
  }
}
