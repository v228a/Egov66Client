package com.vovka.egov66client.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vovka.egov66client.R


class PageAdapter : RecyclerView.Adapter<PageAdapter.PageViewHolder>() {
    
    private val pageRepository = PageRepository()
    private val pages = pageRepository.getAllPages()
    
    // Увеличиваем количество элементов для бесконечного пролистывания
    private val totalItems = Constants.MAX_PAGES * Constants.MAX_NUMERIC_COUNTER // 6000 элементов
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_page, parent, false)
        return PageViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val pageData = pageRepository.getPageByPosition(position)
        holder.bind(pageData)
    }
    
    override fun getItemCount(): Int = totalItems
    
    // Получаем реальную позицию страницы (0-5)
    fun getActualPagePosition(position: Int): Int {
        return position % Constants.MAX_PAGES
    }
    
    // Получаем данные страницы по позиции
    fun getPageData(position: Int): PageData {
        return pageRepository.getPageByPosition(position)
    }
    
    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPageNumber: TextView = itemView.findViewById(R.id.tvPageNumber)
        
        fun bind(pageData: PageData) {
            tvPageNumber.text = pageData.title
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, pageData.colorRes))
        }
    }
}
