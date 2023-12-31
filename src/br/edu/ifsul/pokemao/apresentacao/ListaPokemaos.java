package br.edu.ifsul.pokemao.apresentacao;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import br.edu.ifsul.pokemao.model.PokemaoTreinador;
import br.edu.ifsul.pokemao.persistencia.PokemaoTreinadorRepository;
import br.edu.ifsul.pokemao.persistencia.TreinadorRepository;

public class ListaPokemaos extends JPanel {
    /**
     * Classe responsável por exibir uma lista de PokemaoTreinador na interface
     * gráfica.<p>
     * A lista é exibida em um JPanel, que é adicionado a um JScrollPane.
     * 
     * @param treinadorRepository Repositório de treinadores, para acesso ao
     *                            treinador logado
     * @param array               A lista de Pokemãos a ser exibida.
     * @param contexto            O contexto em que a lista está sendo exibida. O
     *                            contexto pode ser:
     *                            <ul>
     *                            <li>troca</li>
     *                            <li>batalha</li>
     *                            <li>telatreinador</li>
     *                            </ul>
     */
    ListaPokemaos(TreinadorRepository treinadorRepository, ArrayList<PokemaoTreinador> array, String contexto) {
        // configurações do painel
        this.setLayout(null);
        int totalHeight = array.size() * 60;
        this.setPreferredSize(new Dimension(500, totalHeight));
        int y = 0;

        // para cada pokemao na lista, criar um painel menor com as informações dele
        for (PokemaoTreinador pokemao : array) {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.setBounds(0, y, 550, 50);
            panel.setBackground(pokemao.getPokemao().getRaridadeColor());
            this.add(panel);

            y += 60;

            JLabel emoji = new JLabel(pokemao.getPokemao().getEmoji());
            emoji.setBounds(10, 5, 50, 50);
            Font font = new Font("Segoe UI Emoji", Font.PLAIN, 30);
            emoji.setFont(font);
            panel.add(emoji);

            JLabel nome = new JLabel(pokemao.getNome());
            nome.setBounds(60, 10, 200, 30);
            panel.add(nome);

            JLabel hp = new JLabel("HP: " + pokemao.getHp());
            hp.setBounds(250, 10, 100, 30);
            panel.add(hp);

            JLabel dispTroca = new JLabel();
            if (pokemao.isDisponivelParaTroca()) {
                dispTroca.setText("Disp.: Sim");
            } else {
                dispTroca.setText("Disp.: Não");
            }
            dispTroca.setBounds(350, 10, 100, 30);
            panel.add(dispTroca);

            // ações do painel
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (contexto) {
                        case "troca":
                            new EscolherTroca(treinadorRepository, pokemao);
                            SwingUtilities.getWindowAncestor(panel).dispose();
                            break;
                        case "batalha":
                            // verificar se o HP está cheio
                            if (pokemao.getHp() < 100) {
                                int opcao = JOptionPane.showConfirmDialog(null, "Deseja curar " + pokemao.getNome() + "?");
                                if (opcao == JOptionPane.YES_OPTION) {
                                    pokemao.fullHp();
                                    new PokemaoTreinadorRepository().curar(pokemao);
                                }
                            }
                            new TelaBatalha(treinadorRepository, pokemao);
                            SwingUtilities.getWindowAncestor(panel).dispose();
                            break;
                        case "telatreinador":
                            Object[] options = { "Curar", "Disponível para troca", "Libertar", "Alterar nome" };
                            int n = JOptionPane.showOptionDialog(null,
                                    "O que deseja fazer com " + pokemao.getNome() + "?",
                                    "Opções",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[3]);
                            switch (n) {
                                case 0:
                                    new PokemaoTreinadorRepository().curar(pokemao);
                                    JOptionPane.showMessageDialog(null, pokemao.getNome() + " foi curado!");
                                    break;
                                case 1:
                                    if (pokemao.isDisponivelParaTroca()) {
                                        pokemao.setDisponivelParaTroca(false);
                                        JOptionPane.showMessageDialog(null,
                                                pokemao.getNome() + " não está mais disponível para troca!");
                                    } else {
                                        pokemao.setDisponivelParaTroca(true);
                                        JOptionPane.showMessageDialog(null,
                                                pokemao.getNome() + " está disponível para troca!");
                                    }
                                    break;
                                case 2:
                                    new PokemaoTreinadorRepository().libertar(pokemao);
                                    JOptionPane.showMessageDialog(null, pokemao.getNome() + " foi libertado!");
                                    break;
                                case 3:
                                    String novoNome = JOptionPane.showInputDialog(null, "Novo nome:");
                                    if (novoNome != null) {
                                        pokemao.setNome(novoNome);
                                        new PokemaoTreinadorRepository().editar(pokemao);
                                        JOptionPane.showMessageDialog(null, "Nome alterado com sucesso!");
                                    }
                                    break;
                                default:
                                    break;
                            }
                            SwingUtilities.getWindowAncestor(panel).dispose();
                            new MeusPokemaos(treinadorRepository);
                            break;
                        default:
                            break;
                    }
                }

            });

            this.add(panel);
        }

        // tornando o painel visível
        this.setVisible(true);
    }
}
