package com.caselchen.designpattern.factory;

/**
 * 使用建设者模式(Builder)创建ConstructorArg
 */
public class ConstructorArg {

    private boolean isRef;
    private Class type;
    private Object arg;

    private ConstructorArg(Builder builder) {
        this.isRef = builder.isRef;
        this.type = builder.type;
        this.arg = builder.arg;
    }

    public boolean getIsRef() {
        return isRef;
    }

    public Class getType() {
        return type;
    }

    public Object getArg() {
        return arg;
    }

    public static class Builder {

        private boolean isRef;
        private Class type;
        private Object arg;

        public ConstructorArg build() {
            if (arg == null) {
                throw new IllegalArgumentException("The value of parameter `arg` should be provided.");
            }
            if (!this.isRef && type == null) {
                throw new IllegalArgumentException("The value of parameter `type` should be provided if `isRef` == false.");
            }
            return new ConstructorArg(this);
        }

        public Builder setRef(boolean isRef) {
            this.isRef = isRef;
            return this;
        }

        public Builder setType(Class type) {
            this.type = type;
            return this;
        }

        public Builder setArg(Object arg) {
            this.arg = arg;
            return this;
        }
    }

}
