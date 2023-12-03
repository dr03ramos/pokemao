package br.edu.ifsul.pokemao.model;

public class Pokemao {
    private long id;

    private String emoji;
    private String nome;

    private int ataque;
    private int defesa;
    private int hp;
    private int velocidade;
    private int raridade;

    private String descricao;

    public Pokemao(long id, String emoji, String nome, int ataque, int defesa, int hp, int raridade, String descricao) {
        this.id = id;
        this.emoji = emoji;
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.hp = hp;
        this.velocidade = 20;
        this.raridade = raridade;
        this.descricao = descricao;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }


    public int getRaridade() {
        return raridade;
    }


    public void setRaridade(int raridade) {
        this.raridade = raridade;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) { 
        this.descricao = descricao;
    }
}