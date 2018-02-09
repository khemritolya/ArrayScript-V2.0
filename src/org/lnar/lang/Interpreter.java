package org.lnar.lang;

import org.lnar.ui.OutputPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
    private static final String failcode =      "38912738213878173817239172319219381098239102831092381902381928387";
    private static final String successcode =   "12753176437699087287351363265123465234165342836843876761234816253";
    private static int[] vals;
    private static int index;
    private static OutputPane p;
    private static ArrayList<Integer> labels = new ArrayList<>();

    public static void interpret(File f, OutputPane pane) throws IOException {
        if (!f.getName().endsWith(".as")) throw new IOException("Not a proper ArrayScript file!");

        p = pane;

        Scanner scanner = new Scanner(f);
        ArrayList<String> ins = new ArrayList<>();

        while (scanner.hasNext()) {
            ins.add(scanner.nextLine());
        }

        labels.clear();
        for (int i = 0; i < ins.size(); i++) {
            if (ins.get(i).startsWith("lb")) labels.add(i);
        }

        for (index = 0; index < ins.size(); index++) {
            if (exec(ins.get(index)).equals(failcode)) {
                print("Failure in line: " + (index + 1));
                scanner.close();
                return;
            }
        }

        scanner.close();

        return;
    }

    private static String exec(String line) {
        if (!match(line)) return failcode;
        if (line.startsWith("#")) return successcode;
        if (line.length() == 0) return successcode;

        line = line.replaceAll(" ", "");

        if (line.startsWith("rz")) {
            try {
                int nl = Integer.parseInt(exec(line.substring(3, line.length() - 1)));

                if (vals != null && nl == vals.length) return "" + nl;

                resize(nl);
                return "" + nl;
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("rd")) {
            try {
                int index = Integer.parseInt(exec(line.substring(3, line.length() - 1)));
                return "" + vals[index];
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("wr")) {
            try {
                int splitIndex = split(line);

                int index = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int value = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                vals[index] = value;

                return "" + value;
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("gt")) {
            try {
                int gt = labels.get(Integer.parseInt(exec(line.substring(3, line.length() - 1))));
                index = gt;
                return successcode;
            } catch (Exception e) {
                e.printStackTrace();
                return failcode;
            }
        } else if (line.startsWith("cl")) {
            try {
                int splitIndex = split(line);

                int index = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int length = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                for (int i = 0; i < length; i++) {
                    vals[i + index] = 0;
                }

                return successcode;
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("as")) {
            try {
                int splitIndex = split(line);

                String a = exec(line.substring(3, splitIndex));
                String b = exec(line.substring(splitIndex + 1, line.length() - 1));

                return a + b;
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("ad")) {
            try {
                int splitIndex = split(line);

                int a = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int b = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                return "" + (a + b);
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("sb")) {
            try {
                int splitIndex = split(line);

                int a = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int b = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                return "" + (a - b);
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("ml")) {
            try {
                int splitIndex = split(line);

                int a = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int b = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                return "" + (a * b);
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("dv")) {
            try {
                int splitIndex = split(line);

                int a = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int b = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                return "" + (a / b);
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("md")) {
            try {
                int splitIndex = split(line);

                int a = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int b = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                return "" + (a % b);
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("im")) {
            try {
                int sIndex = index;
                ArrayList<Integer> sLabels = (ArrayList<Integer>) labels.clone();
                interpret(new File(exec(line.substring(3, line.length() - 1)) + ".as"), p);
                index = sIndex;
                labels = sLabels;
                return successcode;
            } catch (Exception e) {
                print("Could not import library!\n");
                return failcode;
            }
        } else if (line.startsWith("ui")) {
            try {
                return successcode;
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("ts")) {
            try {
                int splitIndex = split(line);

                int index = Integer.parseInt(exec(line.substring(3, splitIndex)));
                int length = Integer.parseInt(exec(line.substring(splitIndex + 1, line.length() - 1)));

                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < length; i++) {
                    sb.append((char)vals[index + i]);
                }

                return sb.toString();
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("us")) {
            try {
                int splitIndex = split(line);

                int index = Integer.parseInt(exec(line.substring(3, splitIndex)));
                String text = exec(line.substring(splitIndex + 1, line.length() - 1));

                text = fix(text);

                for (int i = 0; i < text.length(); i++) {
                    vals[i + index] = text.charAt(i);
                }

                return successcode;
            } catch (Exception e) {
                return failcode;
            }
        } else if (line.startsWith("pr")) {
            try {
                String out = exec(line.substring(3, line.length() - 1));
                if (out.equals(failcode)) return failcode;

                out  = fix(out);

                print(out);
                return out;
            } catch (Exception e) {
                return failcode;
            }
        } else {
            return fix(line);
        }
    }

    private static String fix(String t) {
        return t.replaceAll("/_", " ").replaceAll("/n", "\n").replaceAll("/t", "\t")
                .replaceAll("/m", vals != null ? vals.length + "" : "0");
    }

    private static int split(String in) {
        int unmatchedParens = -1;
        for (int i = 0; i < in.length(); i++) {
            if (unmatchedParens == 0 && in.charAt(i) == ',') return i;
            if (in.charAt(i) == '(') unmatchedParens++;
            if (in.charAt(i) == ')') unmatchedParens--;
        }

        return -1;
    }

    private static boolean match(String in) {
        int unmatchedParens = 0;

        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == '(') unmatchedParens++;
            if (in.charAt(i) == ')') unmatchedParens--;
        }

        return unmatchedParens == 0;
    }

    private static void resize(int l) {
        if (vals == null) {
            vals = new int[l];
            return;
        }

        int[] vals_copy = new int[l];

        for (int i = 0; i < (l < vals.length ? l : vals.length); i++) {
            vals_copy[i] = vals[i];
        }

        vals = vals_copy;
    }

    private static void print(String s) {
        p.write(s);
    }
}