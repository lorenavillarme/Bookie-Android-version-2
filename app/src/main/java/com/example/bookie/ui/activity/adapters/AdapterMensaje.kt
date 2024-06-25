import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bookie.R
import com.example.bookie.data.model.idk.Mensaje


/**
 * ViewModel para la gestión de libros en la aplicación.
 * @property myId Id propio del usuario
 * @author Lorena Villar
 * @version 4/4/2024
 * @see Mensaje
 *
 * */

class AdapterMensaje(
    val myId: String?
) : RecyclerView.Adapter<ViewHolder>() {

    private val mensajes = ArrayList<Mensaje>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_chat_der, parent, false)

            MensajeViewHolderDer(view)

        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_chat_izq, parent, false)
            MensajeViewHolderIzq(view)

        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mensaje = mensajes[position]

        if (getItemViewType(position) == 0) {
            val dereHolder = holder as MensajeViewHolderDer
            dereHolder.bind(mensaje)
        }else {
            val izqHolder = holder as MensajeViewHolderIzq
            izqHolder.bind(mensaje)
        }
    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

    inner class MensajeViewHolderDer(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val texto_mensaje: TextView = itemView.findViewById(R.id.tv_mensaje)
        private val texto_fecha: TextView = itemView.findViewById(R.id.tv_hora)

        fun bind(mensaje: Mensaje) {
            texto_mensaje.text = mensaje.message
            texto_fecha.text = mensaje.fecha
        }
    }

    inner class MensajeViewHolderIzq(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val texto_mensaje: TextView = itemView.findViewById(R.id.tv_mensaje)
        private val texto_fecha: TextView = itemView.findViewById(R.id.tv_hora)

        fun bind(mensaje: Mensaje) {
            texto_mensaje.text = mensaje.message
            texto_fecha.text = mensaje.fecha

        }
    }

    fun actualizarMensajes(nuevosMensajes: List<Mensaje>) {
        mensajes.clear()
        mensajes.addAll(nuevosMensajes)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val mensaje = mensajes[position]
        return if (mensaje.user == myId?.toDouble()?.toInt()) {
            0
        } else
            1
    }
}
