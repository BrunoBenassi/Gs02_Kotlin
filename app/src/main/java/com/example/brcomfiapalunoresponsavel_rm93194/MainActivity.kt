package com.example.brcomfiapalunoresponsavel_rm93194



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView

class PrincipalActivity : AppCompatActivity() {

    private lateinit var listaRecyclerView: RecyclerView
    private lateinit var barraPesquisa: SearchView
    private lateinit var adaptadorEcoDica: Adapter
    private lateinit var bancoDadosHelper: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaRecyclerView = findViewById(R.id.recyclerView)
        barraPesquisa = findViewById(R.id.searchView)
        listaRecyclerView.layoutManager = LinearLayoutManager(this)

        bancoDadosHelper = Database(this)

        if (bancoDadosHelper.fetchAllTips().isEmpty()) {
            adicionarDicasIniciais()
        }

        val listaDicas = bancoDadosHelper.fetchAllTips()
        adaptadorEcoDica = Adapter(listaDicas)
        listaRecyclerView.adapter = adaptadorEcoDica

        barraPesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(consulta: String?): Boolean {
                adaptadorEcoDica.filter.filter(consulta)
                return false
            }

            override fun onQueryTextChange(textoNovo: String?): Boolean {
                adaptadorEcoDica.filter.filter(textoNovo)
                return false
            }
        })
    }

    private fun adicionarDicasIniciais() {
        val dicasPadrao = listOf(
            BDTips("Desconectar dispositivos não utilizados", "Dispositivos eletrônicos ainda consomem energia em modo de espera. Desconecte quando não necessário."),
            BDTips("Prefira lâmpadas LED", "Lâmpadas LED são mais eficientes e consomem até 80% menos eletricidade."),
            BDTips("Economize água", "A água é um recurso escasso. Reduza o tempo no banho e evite desperdícios."),
            BDTips("Compre de produtores locais", "Produtos locais não necessitam de transporte a longas distâncias, diminuindo a emissão de poluentes.")
        )
        dicasPadrao.forEach { bancoDadosHelper.addTip(it) }
    }
}
