package one.digitalinnovation.labpadroesprojetospring.service;


import one.digitalinnovation.labpadroesprojetospring.model.Cliente;
import one.digitalinnovation.labpadroesprojetospring.model.ClienteRepository;
import one.digitalinnovation.labpadroesprojetospring.model.Endereco;
import one.digitalinnovation.labpadroesprojetospring.model.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link org.springframework.beans.factory.annotation.Autowired}).
 * Com isso, como essa classe é um {@link Service}, ele será tratada como um <b>Singleton</b>
 */
@Service
public class ClienteServiceImpl implements ClienteService{

    //TO DO Singleton: Injetar os componentes do Spring com @Autowired
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;



    //TO DO Strategy: Implementar os métodos definidos na interface.
    //To DO FAcade: Abstrair intergrações com subsistemas, provendo uma interface simples
    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }


    @Override
    public void inserir(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
    Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {

        Endereco novoEndereco = viaCepService.consultarCep(cep);
        enderecoRepository.save(novoEndereco);
        return novoEndereco;
    });
    cliente.setEndereco(endereco);
    clienteRepository.save(cliente);
    }


    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            String cep = cliente.getEndereco().getCep();
            Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {

                Endereco novoEndereco = viaCepService.consultarCep(cep);
                enderecoRepository.save(novoEndereco);
                return novoEndereco;
            });
            cliente.setEndereco(endereco);
            clienteRepository.save(cliente);
        };
        }


    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);

    }
    
}

/**
 private void salvarClienteComCep(Cliente cliente) {
 String cep = cliente.getEndereco().getCep();
 Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
 Endereco novoEndereco = viaCepService.consultarCep(cep);
 enderecoRepository.save(novoEndereco);
 return novoEndereco;
 });
 cliente.setEndereco(endereco);
 clienteRepository.save(cliente);
 }
 */
