package com.sirkaue.screensound.menu;

import com.sirkaue.screensound.model.Artista;
import com.sirkaue.screensound.model.Musica;
import com.sirkaue.screensound.model.TipoArtista;
import com.sirkaue.screensound.repository.ArtistaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private final Scanner sc = new Scanner(System.in);
    private final ArtistaRepository repository;

    public Menu(ArtistaRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {

        int opcao = -1;

        while (opcao != 0) {
            var menu = """
                    *** Screen Sound Músicas ***
                                        
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                                        
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrarArtistas() {
        String cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("S")) {
            System.out.println("Informe o nome desse artista: ");
            String nome = sc.nextLine();
            System.out.println("Informe o tipo desse artista: (solo, dupla ou banda) ");
            String tipo = sc.nextLine();

            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);

            repository.save(artista);
            System.out.println("Cadastrar novo artista? (S/N) ");
            cadastrarNovo = sc.nextLine();
        }
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar música de qual artista? ");
        String nome = sc.nextLine();
        Optional<Artista> artista = repository.findByNomeContainingIgnoreCase(nome);

        if (artista.isPresent()) {
            System.out.println("Informe o título da música: ");
            String nomeMusica = sc.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repository.save(artista.get());
        } else {
            System.out.println("Artista não encontrado (a)");
        }
    }

    private void listarMusicas() {
        List<Artista> artistas = repository.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Buscar músicas de qual artista? ");
        String nome = sc.nextLine();
        List<Musica> musicas = repository.buscaMusicaPorArtistas(nome);
        musicas.forEach(System.out::println);
    }
}
