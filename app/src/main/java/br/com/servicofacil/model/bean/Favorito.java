package br.com.servicofacil.model.bean;

import java.io.Serializable;

/**
 * Created by Aretha on 25/02/2016.
 */
public class Favorito implements Serializable {
    private Long id;
    private Usuario usuario;
    private Usuario usuarioFavoritado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioFavoritado() {
        return usuarioFavoritado;
    }

    public void setUsuarioFavoritado(Usuario usuarioFavoritado) {
        this.usuarioFavoritado = usuarioFavoritado;
    }
}
