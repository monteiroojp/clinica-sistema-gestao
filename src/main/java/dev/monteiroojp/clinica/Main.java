package dev.monteiroojp.clinica;

import dev.monteiroojp.clinica.excecao.TelefoneInvalidoException;
import dev.monteiroojp.clinica.modelo.valueObject.Cpf;
import dev.monteiroojp.clinica.excecao.CpfInvalidoException;
import dev.monteiroojp.clinica.modelo.valueObject.Telefone;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       TESTE COMPLETO - CPF             ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        testeValidacao();
        testeEquals();
        testeHashCode();
        testeToString();
        testeHashMap();
        testeHashSet();
    }

    private static void testeValidacao() {
        System.out.println("=== 1. TESTE DE VALIDAÇÃO ===\n");

        // CPF válido formatado
        testarCPF("123.456.789-00", true);

        // CPF válido sem formatação
        testarCPF("12345678900", true);

        // CPF null
        testarCPF(null, false);

        // CPF com dígitos iguais
        testarCPF("111.111.111-11", false);

        // CPF inválido
        testarCPF("123.456", false);

        System.out.println();
    }

    private static void testarCPF(String cpfStr, boolean devePassar) {
        try {
            Cpf cpf = new Cpf(cpfStr);
            if (devePassar) {
                System.out.println("✓ PASSOU: " + cpfStr + " → " + cpf.getValue());
            } else {
                System.out.println("✗ FALHOU: " + cpfStr + " deveria rejeitar!");
            }
        } catch (CpfInvalidoException e) {
            if (!devePassar) {
                System.out.println("✓ REJEITADO: " + cpfStr);
            } else {
                System.out.println("✗ FALHOU: " + cpfStr + " deveria aceitar!");
            }
        }
    }

    private static void testeEquals() {
        System.out.println("=== 2. TESTE DE EQUALS ===\n");

        Cpf cpf1 = new Cpf("123.456.789-00");
        Cpf cpf2 = new Cpf("12345678900");  // Mesmo CPF
        Cpf cpf3 = new Cpf("987.654.321-00");  // CPF diferente

        System.out.println("cpf1: " + cpf1.getValue());
        System.out.println("cpf2: " + cpf2.getValue());
        System.out.println("cpf3: " + cpf3.getValue());
        System.out.println();

        // Teste identidade
        System.out.println("cpf1 == cpf1: " + (cpf1 == cpf1));  // true
        System.out.println("cpf1 == cpf2: " + (cpf1 == cpf2));  // false (objetos diferentes)
        System.out.println();

        // Teste equals
        System.out.println("cpf1.equals(cpf1): " + cpf1.equals(cpf1));  // true
        System.out.println("cpf1.equals(cpf2): " + cpf1.equals(cpf2));  // true (valores iguais!)
        System.out.println("cpf1.equals(cpf3): " + cpf1.equals(cpf3));  // false
        System.out.println("cpf1.equals(null): " + cpf1.equals(null));  // false
        System.out.println("cpf1.equals(\"123\"): " + cpf1.equals("123"));  // false
        System.out.println();
    }

    private static void testeHashCode() {
        System.out.println("=== 3. TESTE DE HASHCODE ===\n");

        Cpf cpf1 = new Cpf("123.456.789-00");
        Cpf cpf2 = new Cpf("12345678900");  // Mesmo CPF
        Cpf cpf3 = new Cpf("987.654.321-00");

        int hash1 = cpf1.hashCode();
        int hash2 = cpf2.hashCode();
        int hash3 = cpf3.hashCode();

        System.out.println("cpf1.hashCode(): " + hash1);
        System.out.println("cpf2.hashCode(): " + hash2);
        System.out.println("cpf3.hashCode(): " + hash3);
        System.out.println();

        // Verificar contrato: equals → mesmo hash
        if (cpf1.equals(cpf2)) {
            if (hash1 == hash2) {
                System.out.println("✓ CONTRATO OK: cpf1.equals(cpf2) e hash iguais");
            } else {
                System.out.println("✗ CONTRATO VIOLADO: equals true mas hash diferente!");
            }
        }

        System.out.println();
    }

    private static void testeToString() {
        System.out.println("=== 4. TESTE DE TOSTRING ===\n");

        Cpf cpf = new Cpf("123.456.789-00");

        System.out.println("toString(): " + cpf.toString());
        System.out.println("Implícito: " + cpf);  // Chama toString() automaticamente
        System.out.println();
    }

    private static void testeHashMap() {
        System.out.println("=== 5. TESTE EM HASHMAP ===\n");

        Map<Cpf, String> pacientes = new HashMap<>();

        Cpf cpf1 = new Cpf("123.456.789-00");
        Cpf cpf2 = new Cpf("12345678900");  // Mesmo CPF, formato diferente
        Cpf cpf3 = new Cpf("987.654.321-00");

        // Adiciona com cpf1
        pacientes.put(cpf1, "João Silva");
        pacientes.put(cpf3, "Maria Santos");

        System.out.println("Adicionados:");
        System.out.println("  cpf1 → João Silva");
        System.out.println("  cpf3 → Maria Santos");
        System.out.println();

        // Busca com cpf2 (mesmo CPF, objeto diferente)
        System.out.println("Buscas:");
        System.out.println("  get(cpf1): " + pacientes.get(cpf1));  // João Silva
        System.out.println("  get(cpf2): " + pacientes.get(cpf2));  // João Silva ✓
        System.out.println("  get(cpf3): " + pacientes.get(cpf3));  // Maria Santos

        if (pacientes.get(cpf2) != null) {
            System.out.println("\n✓ HASHMAP OK: Encontrou com CPF diferente mas igual!");
        } else {
            System.out.println("\n✗ HASHMAP FALHOU: Não encontrou!");
        }

        System.out.println();
    }

    private static void testeHashSet() {
        System.out.println("=== 6. TESTE EM HASHSET ===\n");

        Set<Cpf> cpfs = new HashSet<>();

        Cpf cpf1 = new Cpf("123.456.789-00");
        Cpf cpf2 = new Cpf("12345678900");  // Mesmo CPF
        Cpf cpf3 = new Cpf("987.654.321-00");

        cpfs.add(cpf1);
        cpfs.add(cpf2);  // Não deve adicionar (igual a cpf1)
        cpfs.add(cpf3);

        System.out.println("Adicionados: cpf1, cpf2 (duplicado), cpf3");
        System.out.println("Tamanho do Set: " + cpfs.size());  // Deve ser 2

        if (cpfs.size() == 2) {
            System.out.println("✓ HASHSET OK: Não adicionou duplicado!");
        } else {
            System.out.println("✗ HASHSET FALHOU: Adicionou duplicado!");
        }

        System.out.println();

        Telefone tel1 = new Telefone("(31) 98888-1111");
        System.out.println(tel1.getValue());        // 31988881111
        System.out.println(tel1.getValueFormatado()); // (31) 98888-1111

        // Teste 2: Fixo formatado
        Telefone tel2 = new Telefone("(31) 3333-4444");
        System.out.println(tel2.getValue());        // 3133334444
        System.out.println(tel2.getValueFormatado()); // (31) 3333-4444

        // Teste 3: Sem formatação
        Telefone tel3 = new Telefone("31988881111");
        System.out.println(tel3.getValue());        // 31988881111

        // Teste 4: Equals
        Telefone tel4 = new Telefone("31988881111");
        System.out.println(tel1.equals(tel4));  // true (mesmo número!)

        // Teste 5: Inválido
        try {
            new Telefone("123");  // Muito curto
        } catch (TelefoneInvalidoException e) {
            System.out.println("✓ Rejeitou corretamente: " + e.getMessage());
        }
    }
}
