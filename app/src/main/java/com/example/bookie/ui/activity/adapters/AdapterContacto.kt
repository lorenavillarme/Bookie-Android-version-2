package com.example.bookie.ui.activity.adapters.contactos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R
import com.example.bookie.data.model.contactos.RespuestaContactoItem
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.utils.sessionmanager.SessionManager
import kotlinx.coroutines.launch

/**
 * Adapter para mostrar contactos
 *@author Lorena Villar
 * @version 13/5/2024
 * @property context Contexto de la aplicación.
 * @property contactos Listado de la respuesta de contacto
 * @property myViewModel implementación de ChatViewModel
 * @property sharedViewModel implementación de SharedViewModel
 * @property listener clickListener
 * @see ChatViewModel
 * @see RespuestaContactoItem
 * @see SharedViewModel
 * @see SessionManager
 * */

class AdapterContacto(
    private val context: Context,
    private val contactos: MutableList<RespuestaContactoItem>,
    private val myViewModel: ChatViewModel,
    private val sharedViewModel: SharedViewModel,
    var listener: OnItemClickListener
) : RecyclerView.Adapter<AdapterContacto.ContactoViewHolder>() {

    fun interface OnItemClickListener {
        fun onItemClick(dataItem: RespuestaContactoItem)
    }

    private lateinit var viewLifeCycleOwner: LifecycleOwner


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        if(!::viewLifeCycleOwner.isInitialized){
            viewLifeCycleOwner = parent.context as LifecycleOwner
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_mensaje, parent, false)
        return ContactoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        val contacto: RespuestaContactoItem = contactos.get(position)
        holder.bind(contacto)
        holder.itemView.setOnClickListener {
            listener.onItemClick(contacto)
        }

        myViewModel.getChat(contacto.chatId.toLong()).observe(viewLifeCycleOwner){
            if (it?.isNotEmpty() == true) {
                val ultimomensaje = it?.get(it.size - 1)
                holder.mensaje.text = ultimomensaje?.message
            }
        }

        myViewModel.viewModelScope.launch {
            val userIdPedido = myViewModel.postId()
            if (userIdPedido != null) {
                //comprobación para asegurar que el usuario es el contrario a el usuario propio (es decir, es el ajeno)
                if (userIdPedido.toDouble().toInt() != contacto.usuarioEmisorId) {
                    //  myViewModel.getUsuarios(contacto.usuarioEmisorId.toLong())
                    myViewModel.getUsuarios(contacto.usuarioEmisorId.toLong()).observe(viewLifeCycleOwner) { usuario ->
                        val decodedBitmap2 = usuario?.foto?.let {base64ToBitmap(it)}
                        val user_pic: ImageView = holder.itemView.findViewById(R.id.img_user)

                        if (decodedBitmap2 != null){
                            user_pic.setImageBitmap(decodedBitmap2)
                        }else{
                            user_pic.setImageResource(R.drawable.generico)
                        }
                    }
                } else {
                    myViewModel.getUsuarios(contacto.usuarioReceptorId.toLong()).observe(viewLifeCycleOwner) { usuario ->
                        val decodedBitmap2 = usuario?.foto?.let {base64ToBitmap(it)}
                        val user_pic: ImageView = holder.itemView.findViewById(R.id.img_user)

                        if (decodedBitmap2 != null){
                            user_pic.setImageBitmap(decodedBitmap2)
                        }else{
                            user_pic.setImageResource(R.drawable.generico)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return contactos.size
    }

    inner class ContactoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val username: TextView = itemView.findViewById(R.id.tv_user)
        val mensaje: TextView = itemView.findViewById(R.id.tv_mensaje_buzon)

        private val sessionManager = SessionManager(context)
        fun bind(contacto: RespuestaContactoItem) {

            val idChat = contacto.chatId
            val data = idChat.toString()
            sharedViewModel.setData(data)

            val idPropio = sessionManager.fetchUsername()?.lowercase()
            if (idPropio != contacto.usuarioEmisorUsername) {
                username.text = contacto.usuarioEmisorUsername
            } else {
                username.text = contacto.usuarioReceptorUsername
            }
        }
    }

    //función de conversión de imágenes
    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun actualizarListado(listaContactos: List<RespuestaContactoItem>){
        contactos.clear()
        contactos.addAll(listaContactos)
        notifyDataSetChanged()
    }
}
