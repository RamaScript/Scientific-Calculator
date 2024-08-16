package com.ramascript.scientificcalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    // Declaring variables for text views and buttons
    private TextView tvsec;
    private TextView tvMain;
    private Button bac, bc, bbrac1, bbrac2, bsin, bcos, btan, blog, bln, bfact, bsquare, bsqrt, binv, b0, b9, b8, b7, b6, b5, b4, b3, b2, b1, bpi, bmul, bminus, bplus, bequal, bdot, bdiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing all variables
        initializeViews();
        setOnClickListeners();
    }

    private void initializeViews() {
        tvsec = findViewById(R.id.idTVSecondary);
        tvMain = findViewById(R.id.idTVprimary);
        bac = findViewById(R.id.bac);
        bc = findViewById(R.id.bc);
        bbrac1 = findViewById(R.id.bbrac1);
        bbrac2 = findViewById(R.id.bbrac2);
        bsin = findViewById(R.id.bsin);
        bcos = findViewById(R.id.bcos);
        btan = findViewById(R.id.btan);
        blog = findViewById(R.id.blog);
        bln = findViewById(R.id.bln);
        bfact = findViewById(R.id.bfact);
        bsquare = findViewById(R.id.bsquare);
        bsqrt = findViewById(R.id.bsqrt);
        binv = findViewById(R.id.binv);
        b0 = findViewById(R.id.b0);
        b9 = findViewById(R.id.b9);
        b8 = findViewById(R.id.b8);
        b7 = findViewById(R.id.b7);
        b6 = findViewById(R.id.b6);
        b5 = findViewById(R.id.b5);
        b4 = findViewById(R.id.b4);
        b3 = findViewById(R.id.b3);
        b2 = findViewById(R.id.b2);
        b1 = findViewById(R.id.b1);
        bpi = findViewById(R.id.bpi);
        bmul = findViewById(R.id.bmul);
        bminus = findViewById(R.id.bminus);
        bplus = findViewById(R.id.bplus);
        bequal = findViewById(R.id.bequal);
        bdot = findViewById(R.id.bdot);
        bdiv = findViewById(R.id.bdiv);
    }

    private void setOnClickListeners() {
        // Setting click listeners for number buttons
        setNumberButtonListeners();

        // Setting click listeners for operator buttons
        setOperatorButtonListeners();

        // Setting click listeners for other buttons
        setFunctionButtonListeners();
    }

    private void setNumberButtonListeners() {
        b1.setOnClickListener(v -> appendToMainText("1"));
        b2.setOnClickListener(v -> appendToMainText("2"));
        b3.setOnClickListener(v -> appendToMainText("3"));
        b4.setOnClickListener(v -> appendToMainText("4"));
        b5.setOnClickListener(v -> appendToMainText("5"));
        b6.setOnClickListener(v -> appendToMainText("6"));
        b7.setOnClickListener(v -> appendToMainText("7"));
        b8.setOnClickListener(v -> appendToMainText("8"));
        b9.setOnClickListener(v -> appendToMainText("9"));
        b0.setOnClickListener(v -> appendToMainText("0"));
        bdot.setOnClickListener(v -> appendToMainText("."));
    }

    private void setOperatorButtonListeners() {
        bplus.setOnClickListener(v -> appendToMainText("+"));
        bdiv.setOnClickListener(v -> appendToMainText("/"));
        bminus.setOnClickListener(v -> appendToMainText("-"));
        bmul.setOnClickListener(v -> appendToMainText("*"));
        bbrac1.setOnClickListener(v -> appendToMainText("("));
        bbrac2.setOnClickListener(v -> appendToMainText(")"));
        bpi.setOnClickListener(v -> {
            appendToMainText("3.142");
            tvsec.setText(bpi.getText().toString());
        });
    }

    private void setFunctionButtonListeners() {
        bsin.setOnClickListener(v -> appendToMainText("sin"));
        bcos.setOnClickListener(v -> appendToMainText("cos"));
        btan.setOnClickListener(v -> appendToMainText("tan"));
        binv.setOnClickListener(v -> appendToMainText("^(-1)"));
        bln.setOnClickListener(v -> appendToMainText("ln"));
        blog.setOnClickListener(v -> appendToMainText("log"));

        bac.setOnClickListener(v -> {
            tvMain.setText("");
            tvsec.setText("");
        });
        bc.setOnClickListener(v -> {
            String str = tvMain.getText().toString();
            if (!str.isEmpty()) {
                tvMain.setText(str.substring(0, str.length() - 1));
            }
        });
        bsqrt.setOnClickListener(v -> calculateUnaryOperation(Math::sqrt, "√"));
        bsquare.setOnClickListener(v -> calculateUnaryOperation(d -> d * d, "²"));
        bfact.setOnClickListener(v -> {
            String str = tvMain.getText().toString();
            if (str.isEmpty()) {
                showToast("Please enter a valid number.");
            } else {
                try {
                    int value = Integer.parseInt(str);
                    if (value < 0) throw new NumberFormatException();
                    BigInteger fact = factorial(value);
                    tvMain.setText(fact.toString());
                    tvsec.setText(value + "!");
                } catch (NumberFormatException e) {
                    showToast("Invalid input for factorial.");
                }
            }
        });
        bequal.setOnClickListener(v -> {
            try {
                String str = tvMain.getText().toString();
                double result = evaluate(str);
                tvMain.setText(String.valueOf(result));
                tvsec.setText(str);
            } catch (Exception e) {
                showToast("Invalid expression.");
            }
        });
    }

    private void appendToMainText(String text) {
        tvMain.append(text);
    }

    private void calculateUnaryOperation(java.util.function.DoubleUnaryOperator operation, String symbol) {
        String str = tvMain.getText().toString();
        if (str.isEmpty()) {
            showToast("Please enter a valid number.");
        } else {
            try {
                double result = operation.applyAsDouble(Double.parseDouble(str));
                tvMain.setText(String.valueOf(result));
                tvsec.setText(symbol + "(" + str + ")");
            } catch (NumberFormatException e) {
                showToast("Invalid input.");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private BigInteger factorial(int n) {
        if (n == 0 || n == 1) return BigInteger.ONE;
        BigInteger fact = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    private double evaluate(String str) {
        return new Object() {
            private int pos = -1;
            private int ch;

            private void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            private boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            private double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            private double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            private double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            private double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());
                return x;
            }
        }.parse();
    }
}
