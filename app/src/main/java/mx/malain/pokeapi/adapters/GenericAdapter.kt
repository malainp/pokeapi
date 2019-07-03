package mx.malain.pokeapi.adapters

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T>(val context: Context, private var clickListener: OnViewHolderClickListener<T>?) :
    RecyclerView.Adapter<GenericAdapter<T>.ViewHolder>() {

    private var items: MutableList<T>
    private var longClickListener: OnViewHolderLongClickListener<T>? = null
    protected var onBind: Boolean = false

    var list: MutableList<T>
        get() = items
        set(list) {
            items = list
            notifyDataSetChanged()
        }

    interface OnViewHolderClickListener<T> {
        fun onClick(view: View, position: Int, item: T?)
    }

    interface OnViewHolderLongClickListener<T> {
        fun onLongClick(view: View, position: Int, item: T?)
    }

    inner class ViewHolder(
        view: View,
        clicklistener: OnViewHolderClickListener<T>?,
        longClickListener: OnViewHolderLongClickListener<*>?
    ) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        private val views: SparseArray<View> = SparseArray()

        val view: View?
            get() = getView(0)

        init {
            views.append(0, view)

            if (clicklistener != null)
                view.setOnClickListener(this)
            if (longClickListener != null)
                view.setOnLongClickListener(this)
        }

        override fun onClick(view: View) {
            if (clickListener != null)
                clickListener?.onClick(view, adapterPosition, getItem(adapterPosition))
        }

        override fun onLongClick(view: View): Boolean {
            if (longClickListener != null)
                longClickListener?.onLongClick(view, adapterPosition, getItem(adapterPosition))
            return true
        }

        fun initViewList(idList: IntArray) {
            for (id in idList)
                initViewById(id)
        }

        private fun initViewById(id: Int) {
            val view = if (view != null) view!!.findViewById<View>(id) else null
            if (view != null)
                views.append(id, view)
        }

        fun getView(id: Int): View {
            if (views.get(id) != null)
                return views.get(id)
            else
                initViewById(id)
            return views.get(id)
        }
    }

    protected abstract fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View

    protected abstract fun bindView(item: T?, position: Int, viewHolder: ViewHolder)

    init {
        items = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(createView(context, parent, viewType), clickListener, longClickListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //onBind = true
        bindView(getItem(position), position, viewHolder)
        //onBind = false
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(index: Int): T? {
        return if (index < items.size) items[index] else null
    }

    fun setClickListener(listener: OnViewHolderClickListener<T>) {
        this.clickListener = listener
    }

    fun setOnLongClickListener(listener: OnViewHolderLongClickListener<T>) {
        this.longClickListener = listener
    }

    fun addAll(list: List<T>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun addAt(position: Int, item: T) {
        if (items.isEmpty()) {
            items.add(item)
        } else {
            items.add(position, item)
        }
        notifyDataSetChanged()
    }

    fun reset() {
        items.clear()
        notifyDataSetChanged()
    }

}