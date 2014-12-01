package DB;

import android.provider.BaseColumns;

public final class OficinaContract {

    public OficinaContract(){}

    public final class Oficina implements BaseColumns{
        public final static String TABLE_NAME = "OFICINA";
        public final static String ID_OFICINA = "id_oficina";
        public final static String CURSO = "curso";
        public final static String TITULO = "titulo";
        public final static String IMAGEM = "imagem";
        public final static String DATA_HORA = "data_hora";
        public final static String DURACAO = "duracao";
        public final static String DESCRICAO = "descricao";
        public final static String RESPONSAVEL = "responsavel";
        public final static String LOCAL = "local";
        public final static String VAGAS = "vagas";
        public final static String INSCRITOS = "inscritos";

    }

    public final class Estudante implements BaseColumns{
        public final static String TABLE_NAME = "ESTUDANTE";
        public final static String ID_ESTUDANTE = "id_estudante";
        public final static String ID_OFICINA = "id_oficina";
        public final static String NOME = "nome";
        public final static String EMAIL = "email";
        public final static String TELEFONE = "telefone";
        public final static String CIDADE = "cidade";

    }

}