package br.com.servicofacil.model.bean;

import java.io.Serializable;

/**
 * Created by Ezequiel on 27/11/2015.
 */
public class Comentario implements Serializable {
    private Long id;
    private Usuario usuarioComentando;
    private Usuario usuarioComentado;
    private String comentario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuarioComentando() {
        return usuarioComentando;
    }

    public void setUsuarioComentando(Usuario usuarioComentando) {
        this.usuarioComentando = usuarioComentando;
    }

    public Usuario getUsuarioComentado() {
        return usuarioComentado;
    }

    public void setUsuarioComentado(Usuario usuarioComentado) {
        this.usuarioComentado = usuarioComentado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
