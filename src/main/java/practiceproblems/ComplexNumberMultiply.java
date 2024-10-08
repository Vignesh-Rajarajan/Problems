package practiceproblems;

/**
 * https://leetcode.com/problems/complex-number-multiplication/
 */
public class ComplexNumberMultiply {

    public String complexNumberMultiply(String num1, String num2) {
        String[] first = num1.split("\\+");
        String[] second = num2.split("\\+");
        int aSq = 0;
        int bSq = -1;
        int ab = 0;

        aSq += Integer.parseInt(first[0]) * Integer.parseInt(second[0]);
        // remove i from the string and multiply so substring(0, length - 1)
        bSq *= Integer.parseInt(first[1].substring(0, first[1].length() - 1)) * Integer.parseInt(second[1].substring(0, second[1].length() - 1));
        ab += (Integer.parseInt(first[0]) * Integer.parseInt(second[1].substring(0, second[1].length() - 1))) +
                (Integer.parseInt(second[0]) * Integer.parseInt(first[1].substring(0, first[1].length() - 1)));

        return (aSq + bSq) + "+" + ab + "i";
    }

    public static void main(String[] args) {
        ComplexNumberMultiply complexNumberMultiply = new ComplexNumberMultiply();
        System.out.println(complexNumberMultiply.complexNumberMultiply("1+1i", "1+1i"));
    }
}
