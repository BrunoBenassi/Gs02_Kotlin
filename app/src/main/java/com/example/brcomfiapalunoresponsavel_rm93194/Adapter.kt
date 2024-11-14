package com.example.brcomfiapalunoresponsavel_rm93194


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Adapter(private var listaDicas: List<BDTips>) : RecyclerView.Adapter<Adapter.DicaViewHolder>(), Filterable {

    private var dicasFiltradas: List<BDTips> = listaDicas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_BDTips, parent, false)
        return DicaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dicaAtual = dicasFiltradas[position]
        holder.vincular(dicaAtual)
    }

    override fun getItemCount(): Int {
        return dicasFiltradas.size
    }

    inner class DicaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitulo: TextView = view.findViewById(R.id.tituloDica)
        private val txtDescricao: TextView = view.findViewById(R.id.descricaoDica)

        fun vincular(dica: BDTips) {
            txtTitulo.text = dica.titulo
            txtDescricao.text = dica.descricao

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Dica: ${dica.titulo}\n${dica.descricao}", Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener {
                val intentNavegador = Intent(Intent.ACTION_VIEW, Uri.parse("https://cebds.org/desenvolvimento-sustentavel-o-que-e-e-objetivos/"))
                itemView.context.startActivity(intentNavegador)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val resultadosFiltro = FilterResults()
                dicasFiltradas = if (query.isNullOrEmpty()) {
                    listaDicas
                } else {
                    listaDicas.filter { dica ->
                        dica.titulo.contains(query, ignoreCase = true) ||
                                dica.descricao.contains(query, ignoreCase = true)
                    }
                }
                resultadosFiltro.values = dicasFiltradas
                return resultadosFiltro
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dicasFiltradas = results?.values as List<BDTips>
                notifyDataSetChanged()
            }
        }
    }
}
