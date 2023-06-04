package com.example.helloworld

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class AdapterKotlin: RecyclerView.Adapter<AdapterKotlin.ViewHolder>() {

    var list: MutableList<SuperHero> = ArrayList()
    lateinit var context: Context
    lateinit var listener: OnManageSuperHeroListener


    fun AdapterKotlin(list: MutableList<SuperHero>, context: Context, listener: OnManageSuperHeroListener) {
        this.list = list
        this.context = context
        this.listener = listener
    }

    interface OnManageSuperHeroListener {
        fun onEditSuperHero(superHero: SuperHero)
        fun onDeleteSuperHero(superHero: SuperHero)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position), context, listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Opción 1
        val superheroName = itemView.findViewById(R.id.tvSuperhero) as TextView
        val realName = itemView.findViewById(R.id.tvRealName) as TextView
        val publisher = itemView.findViewById(R.id.tvPublisher) as TextView
        val avatar = itemView.findViewById(R.id.ivAvatar) as ImageView

        // Opción 2: con binding
        val binding = ItemSuperheroBinding.bind(itemView)


        fun bind(superHero:SuperHero, context: Context, listener: OnManageSuperHeroListener){
            // Opción 1
            superheroName.text = superHero.superhero
            realName.text = superHero.realName
            publisher.text = superHero.publisher
            avatar.loadUrl(superHero.photo)

            // Opción 2
            binding.tvSuperhero.text = superHero.superhero
            binding.tvRealName.text = superHero.realName
            binding.tvPublisher.text = superHero.publisher
            binding.ivAvatar.loadUrl(superHero.photo)

            itemView.setOnClickListener(View.OnClickListener { listener.onEditSuperHero(superHero) })
            itemView.setOnLongClickListener(View.OnLongClickListener {
                listener.onDeleteSuperHero(superHero)
                true
            })
        }

        // Método para cargar una imagen a través de una URL, gracias a la dependency Picasso
        fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this)
        }
    }
}