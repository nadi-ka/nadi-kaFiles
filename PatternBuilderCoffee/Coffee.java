package com.company;

public class Coffee {
    private String milk;
    private String sugar;
    private String cinnamon;


    public String toString() {
        return ("Coffee" + " " + milk + ", " + sugar + ", " + cinnamon);
    }

    public static class Builder {
        private Coffee personalCoffee;

        public Builder() {
            personalCoffee = new Coffee();
        }

        public Builder withMilk(String milk) {
            personalCoffee.milk = milk;
            return this;
        }

        public Builder withSugar(String sugar) {
            personalCoffee.sugar = sugar;
            return this;
        }

        public Builder withCinnamon(String cinnamon) {
            personalCoffee.cinnamon = cinnamon;
            return this;
        }

        public Coffee build() {
            return personalCoffee;
        }
    }


}
