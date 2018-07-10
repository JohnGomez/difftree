package diff;

import entities.Endereco;
import entities.Pessoa;
import org.junit.Test;

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
        endereco2.setComplemento("rio de janeiro");
        endereco2.setNumero(352);


        pessoa1.setIdade(29);
        pessoa1.setNome("Luiza");
        pessoa1.setSobrenome("gomes");
        pessoa1.setEndereco(endereco1);

        pessoa2.setIdade(29);
        pessoa2.setNome("John");
        pessoa2.setSobrenome("gomess");
        pessoa2.setEndereco(endereco2);

        Diff.diff(pessoa1,pessoa2);

    }
}
