package diff;

import entities.Endereco;
import entities.Pessoa;
import entities.Telefone;
import entities.TipoEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiffTest {


    @Test
    public void testDiffTreeTwoObjects() {
        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();

        Endereco endereco1 = new Endereco();
        endereco1.setNome("iguaçu");
        endereco1.setComplemento("são paulo");
        endereco1.setNumero(106);

        Endereco endereco2 = new Endereco();
        endereco2.setNome("irapua");
        endereco2.setComplemento("Rio de janeiro");
        endereco2.setNumero(352);

        List<Telefone> telefones = new ArrayList<Telefone>();
        Telefone telefone = new Telefone();
        telefone.setNumero("789654321");
        telefone.setTipo("qualquer");
        telefones.add(telefone);
        telefone.setNumero("789654321");
        telefone.setTipo("qualquer");
        telefones.add(telefone);

        List<Telefone> telefones2 = new ArrayList<Telefone>();
        telefone = new Telefone();
        telefone.setNumero("98765432");
        telefone.setTipo("NOVO");
        telefones2.add(telefone);
        telefone.setNumero("98765432");
        telefone.setTipo("NOVO");
        telefones2.add(telefone);


        pessoa1.setIdade(29);
        pessoa1.setNome(null);
        pessoa1.setSobrenome("gomes");
        pessoa1.setEndereco(endereco1);
        pessoa1.setTipo(TipoEnum.NORMAL);
        pessoa1.setTelefones(telefones);

        pessoa2.setIdade(29);
        pessoa2.setNome("John");
        pessoa2.setSobrenome("gomess");
        pessoa2.setEndereco(endereco2);
        pessoa2.setTipo(TipoEnum.VIP);
        pessoa2.setTelefones(telefones2);

        Diff.diff(pessoa1,pessoa2);

    }
}
