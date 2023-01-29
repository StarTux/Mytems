package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public final class MytemsTest {
    @Test
    public void test() {
        final Set<Integer> customModelDataSet = new HashSet<>();
        final Set<Character> characterSet = new HashSet<>();
        int min = Integer.MAX_VALUE;
        for (Mytems mytems : Mytems.values()) {
            if (mytems.material == null) {
                throw new IllegalStateException(mytems + ": material is null!");
            }
            if (mytems.customModelData != null) {
                if (mytems.category != MytemsCategory.POCKET_MOB) {
                    if (customModelDataSet.contains(mytems.customModelData)) {
                        throw new IllegalStateException(mytems + ": duplicate custom model data: " + mytems.customModelData);
                    }
                    customModelDataSet.add(mytems.customModelData);
                }
                if (min > mytems.customModelData) min = mytems.customModelData;
            } else {
                System.out.println("No custom model data: " + mytems);
            }
            if (mytems.character != (char) 0) {
                if (characterSet.contains(mytems.character)) {
                    int num = (int) mytems.character;
                    throw new IllegalStateException(mytems + ": duplicate character: " + num + " 0x" + Integer.toHexString(num));
                }
                characterSet.add(mytems.character);
            }
            for (Character it : mytems.characters) {
                if (it == null || it == (char) 0 || it == mytems.character) continue;
                if (characterSet.contains(it)) {
                    int num = (int) it;
                    throw new IllegalStateException(mytems + ": duplicate character: " + num + " 0x" + Integer.toHexString(num));
                }
                characterSet.add(it);
                customModelDataSet.add((int) it);
            }
        }
        int max;
        List<Integer> gaps = new ArrayList<>();
        for (max = min;; max += 1) {
            if (!customModelDataSet.contains(max)) {
                if (!customModelDataSet.contains(max + 1)) {
                    max -= 1;
                    break;
                }
                gaps.add(max);
            }
        }
        System.out.println("// CustomModelData Range: " + min + "..." + max);
        System.out.println("// CustomModelData Gaps: " + gaps);
        System.out.println("// CustomModelData Next: " + (max + 1));
        testCharacters(characterSet);
    }

    private void testCharacters(Set<Character> characterSet) {
        // Character
        List<Character> characterList = new ArrayList<>(characterSet);
        Collections.sort(characterList);
        char min = characterList.get(0);
        char max = min;
        System.out.println("MIN " + strint(min));
        for (int i = 1; i < characterList.size(); i += 1) {
            char chr = characterList.get(i);
            if (chr == (max + (char) 1)) {
                max = chr;
            } else {
                System.out.println("Character Range " + strint((int) min)
                                   + "..." + strint((int) max)
                                   + " (" + ((int) max - (int) min + 1) + ")");
                min = chr;
                max = chr;
                System.out.println("GAP " + ((int) chr - (int) max - 1));
            }
        }
        System.out.println("Character Range " + strint((int) min)
                           + "..." + strint((int) max)
                           + " (" + ((int) max - (int) min + 1) + ")");
    }

    private String strint(int in) {
        if (in < 0xE000 || in > 0xEEEE) {
            return "" + in;
        } else {
            return "0x" + Integer.toHexString(in).toUpperCase();
        }
    }

    /**
     * Rewrite the Mytems enum to make automated changes.
     */
    public void rewriteAllMytems() {
        char top = (char) 0xE2AD;
        for (Mytems mytems : Mytems.values()) {
            Class<?> enclosing = mytems.mytemClass.getEnclosingClass();
            String className = enclosing != null
                ? enclosing.getSimpleName() + "." + mytems.mytemClass.getSimpleName()
                : mytems.mytemClass.getSimpleName();
            String customModelData = mytems.customModelData == null
                ? "null"
                : strint(mytems.customModelData);
            String chrarr = "";
            char character = mytems.character;
            char[] characters = mytems.characters;
            for (int i = 0; i < characters.length; i += 1) {
                char c = characters[i];
                if (c > (char) 0 && c < (char) 0xE000) {
                    char next = ++top;
                    characters[i] = next;
                    if (c == character) character = next;
                }
            }
            for (int i = 0; i < characters.length; i += 1) {
                if (i > 0) chrarr += ", ";
                chrarr += strint((int) characters[i]);
            }
            System.out.println("    " + mytems.name() + "("
                               + className + ".class, "
                               + mytems.material + ", "
                               + customModelData
                               + (mytems.customModelData == null || mytems.customModelData != (int) character || characters.length > 1
                                  ? ", (char) " + strint((int) character)
                                  : "")
                               + (characters.length > 1
                                  ? ", chrarr(" + chrarr + ")"
                                  : "")
                               + ", " + mytems.category
                               + (mytems.animation != null
                                  ? ", Animation." + mytems.animation.name
                                  : "")
                               + "),");
        }
    }
}
