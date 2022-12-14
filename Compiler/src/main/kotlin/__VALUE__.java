import java.util.*;

public class __VALUE__ {

    public enum __TYPE__ {
        VOID,
        NIL,
        INTEGER,
        FLOAT,
        BOOL,
        STRING,
        TABLE,
        SEQ,
        FUNC;

        @Override
        public String toString() {
            switch (this) {
                case NIL -> {
                    return "Nil";
                }
                case INTEGER -> {
                    return "Int";
                }
                case FLOAT -> {
                    return "Float";
                }
                case BOOL -> {
                    return "Bool";
                }
                case STRING -> {
                    return "String";
                }
                case TABLE -> {
                    return "Table";
                }
                case SEQ -> {
                    return "Seq";
                }
                case FUNC -> {
                    return "Func";
                }
            }
            return null;
        }
    }

    public int __intVal;
    public boolean __boolVal;
    public double __floatVal;
    public String __stringVal;
    public HashMap<__VALUE__, __VALUE__> __tableVal;
    public List<__VALUE__> __seqVal;
    public __FUN__ __funVal;

    public HashMap<__VALUE__, __VALUE__> __metatable = null;

    public __TYPE__ __type;

    public __VALUE__() {
        __type = __TYPE__.NIL;
    }

    public __VALUE__(int value) {
        this.__intVal = value;
        this.__type = __TYPE__.INTEGER;
    }

    public __VALUE__(boolean value) {
        this.__boolVal = value;
        this.__type = __TYPE__.BOOL;
    }

    public __VALUE__(double value) {
        this.__floatVal = value;
        this.__type = __TYPE__.FLOAT;
    }

    public __VALUE__(float value) {
        this.__floatVal = value;
        this.__type = __TYPE__.FLOAT;
    }

    public __VALUE__(String value) {
        this.__stringVal = value;
        this.__type = __TYPE__.STRING;
    }

    public __VALUE__(HashMap<__VALUE__, __VALUE__> value) {
        this.__tableVal = new HashMap<>(value);
        this.__type = __TYPE__.TABLE;
    }

    public __VALUE__(__FUN__ value) {
        __funVal = value;
        __type = __TYPE__.FUNC;
    }

    public __VALUE__(List<__VALUE__> value) {
        __seqVal = new ArrayList<>(value);
        __type = __TYPE__.SEQ;
    }

    public static __VALUE__ voidVal() {
        __VALUE__ voidValue = new __VALUE__();
        voidValue.__type = __TYPE__.VOID;
        return voidValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        __VALUE__ value__ = (__VALUE__) o;

        if (__intVal != value__.__intVal) return false;
        if (__boolVal != value__.__boolVal) return false;
        if (Double.compare(value__.__floatVal, __floatVal) != 0) return false;
        if (!Objects.equals(__stringVal, value__.__stringVal)) return false;
        if (!Objects.equals(__tableVal, value__.__tableVal)) return false;
        if (!Objects.equals(__seqVal, value__.__seqVal)) return false;
        if (!Objects.equals(__funVal, value__.__funVal)) return false;
        if (!Objects.equals(__metatable, value__.__metatable)) return false;
        return __type == value__.__type;
    }

    @Override
    public int hashCode() {
        // FIXME?: ?????????????????? ?????? ???? ?????????????????? ??????????
        int result;
        long temp;
        result = __intVal;
        result = 31 * result + (__boolVal ? 1 : 0);
        temp = Double.doubleToLongBits(__floatVal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (__stringVal != null ? __stringVal.hashCode() : 0);
        result = 31 * result + (__tableVal != null ? __tableVal.hashCode() : 0);
        result = 31 * result + (__seqVal != null ? __seqVal.hashCode() : 0);
        result = 31 * result + (__funVal != null ? __funVal.hashCode() : 0);
        result = 31 * result + (__metatable != null ? __metatable.hashCode() : 0);
        result = 31 * result + (__type != null ? __type.hashCode() : 0);
        return result;
    }

    // +++++++++++++++++++++++++++ ???????????? +++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public __VALUE__ __add__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__add");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }

            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal + o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__intVal + o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__intVal + Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__intVal + Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__add");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __add__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__floatVal + o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__floatVal + o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__floatVal + Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__floatVal + Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__add");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __add__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) + o.__intVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) + o.__intVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) + o.__floatVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) + o.__floatVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) + Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) + Integer.parseInt(o.__stringVal));
                            } catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__(Integer.parseInt(__stringVal) + Double.parseDouble(o.__stringVal));
                                } catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__(Double.parseDouble(__stringVal) + Double.parseDouble(o.__stringVal));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__add");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __add__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__add")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__add")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__add")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__add__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__add__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __sub__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__sub");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal - o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__intVal - o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__intVal - Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__intVal - Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__sub");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __sub__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__floatVal - o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__floatVal - o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__floatVal - Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__floatVal - Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__sub");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __sub__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) - o.__intVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) - o.__intVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) - o.__floatVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) - o.__floatVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) - Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) - Integer.parseInt(o.__stringVal));
                            } catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__(Integer.parseInt(__stringVal) - Double.parseDouble(o.__stringVal));
                                } catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__(Double.parseDouble(__stringVal) - Double.parseDouble(o.__stringVal));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__sub");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __sub__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__sub"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__sub")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__sub"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__sub")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__sub"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__sub")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__sub"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__sub")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__sub__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__sub__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to sub a " + this.__type + " with a " + o.__type);
    }


    public __VALUE__ __mul__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__mul");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal * o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__intVal * o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__intVal * Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__intVal * Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__mul");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __mul__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__floatVal * o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__floatVal * o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__floatVal * Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__floatVal * Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__mul");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __mul__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) * o.__intVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) * o.__intVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) * o.__floatVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) * o.__floatVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) * Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) * Integer.parseInt(o.__stringVal));
                            } catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__(Integer.parseInt(__stringVal) * Double.parseDouble(o.__stringVal));
                                } catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__(Double.parseDouble(__stringVal) * Double.parseDouble(o.__stringVal));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__mul");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __mul__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mul"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mul")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mul"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mul")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mul"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mul")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__mul"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__mul")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__mul__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__mul__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to mul a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __div__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__div");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__((double)__intVal / o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__((double)__intVal / o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__((double)__intVal / Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((double)__intVal / Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__div");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __div__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__floatVal / o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__floatVal / o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__floatVal / Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__floatVal / Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__div");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __div__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__((double)Integer.parseInt(__stringVal) / o.__intVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) / o.__intVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__((double)Integer.parseInt(__stringVal) / o.__floatVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) / o.__floatVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__((double)Integer.parseInt(__stringVal) / Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) / Integer.parseInt(o.__stringVal));
                            } catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__((double)Integer.parseInt(__stringVal) / Double.parseDouble(o.__stringVal));
                                } catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__(Double.parseDouble(__stringVal) / Double.parseDouble(o.__stringVal));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__div");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __div__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__div"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__div")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__div"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__div")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__div"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__div")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__div"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__div")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__div__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__div__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to div a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __floor_div__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__idiv");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal / o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__((int)(__intVal / o.__floatVal));
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__intVal / Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((int)(__intVal / Double.parseDouble(o.__stringVal)));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__idiv");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __floor_div__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__((int)(__floatVal / o.__intVal));
                    }
                    case FLOAT -> {
                        return new __VALUE__((int)(__floatVal / o.__floatVal));
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__((int)(__floatVal / Integer.parseInt(o.__stringVal)));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((int)(__floatVal / Double.parseDouble(o.__stringVal)));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__idiv");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __floor_div__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) / o.__intVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((int)(Double.parseDouble(__stringVal) / o.__intVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__((int)(Integer.parseInt(__stringVal) / o.__floatVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((int)(Double.parseDouble(__stringVal) / o.__floatVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) / Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__((int)(Double.parseDouble(__stringVal) / Integer.parseInt(o.__stringVal)));
                            } catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__((int)(Integer.parseInt(__stringVal) / Double.parseDouble(o.__stringVal)));
                                } catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__((int)(Double.parseDouble(__stringVal) / Double.parseDouble(o.__stringVal)));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__idiv");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __floor_div__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__idiv"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__idiv")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__idiv"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__idiv")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__idiv"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__idiv")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__idiv"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__idiv")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__floor_div__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__floor_div__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to idiv a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __pow__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__pow");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__((int)Math.pow(this.__intVal, o.__intVal));
                    }
                    case FLOAT -> {
                        return new __VALUE__((float) Math.pow(this.__intVal, o.__floatVal));
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__((float) Math.pow(this.__intVal, Integer.parseInt(o.__stringVal)));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((float) Math.pow(this.__intVal, Double.parseDouble(o.__stringVal)));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__pow");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __pow__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__((float) Math.pow(this.__floatVal, o.__intVal));
                    }
                    case FLOAT -> {
                        return new __VALUE__((float) Math.pow(this.__floatVal, o.__floatVal));
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__((float) Math.pow(this.__floatVal, Integer.parseInt(o.__stringVal)));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((float) Math.pow(this.__floatVal, Double.parseDouble(o.__stringVal)));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__pow");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __pow__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__((int)Math.pow(Integer.parseInt(__stringVal), o.__intVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((float) Math.pow(Double.parseDouble(__stringVal), o.__intVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__((float) Math.pow(Integer.parseInt(__stringVal), o.__floatVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__((float) Math.pow(Double.parseDouble(__stringVal), o.__floatVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__((int)Math.pow(Integer.parseInt(__stringVal), Integer.parseInt(o.__stringVal)));
                        } catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__((float) Math.pow(Double.parseDouble(__stringVal), Integer.parseInt(o.__stringVal)));
                            } catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__((float) Math.pow(Integer.parseInt(__stringVal), Double.parseDouble(o.__stringVal)));
                                } catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__((float) Math.pow(Double.parseDouble(__stringVal), Double.parseDouble(o.__stringVal)));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__pow");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __pow__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__pow"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__pow")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__pow"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__pow")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__pow"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__pow")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__pow"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__pow")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__pow__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__pow__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to pow a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __xor__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__bxor");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(this.__intVal ^ o.__intVal);
                    }
                    case FLOAT -> {
                        if(o.__floatVal - (int)o.__floatVal != 0) {
                            throw new UnsupportedOperationException("Error: number has no integer representation");
                        }
                        return new __VALUE__(this.__intVal ^ (int)o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(this.__intVal ^ Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                if(Double.parseDouble(o.__stringVal) - (int)Double.parseDouble(o.__stringVal) != 0) {
                                    throw new UnsupportedOperationException("Error: number has no integer representation"); //FIXME
                                }
                                return new __VALUE__(this.__intVal ^ (int)Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__bxor");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __xor__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        if(this.__floatVal - (int)this.__floatVal != 0) {
                            throw new UnsupportedOperationException("Error: number has no integer representation");
                        }
                        return new __VALUE__((int)this.__floatVal ^ o.__intVal);
                    }
                    case FLOAT -> {
                        if(o.__floatVal - (int)o.__floatVal != 0 || this.__floatVal - (int)this.__floatVal != 0) {
                            throw new UnsupportedOperationException("Error: number has no integer representation");
                        }
                        return new __VALUE__((int)this.__floatVal ^ (int)o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            if(this.__floatVal - (int)this.__floatVal != 0) {
                                throw new UnsupportedOperationException("Error: number has no integer representation");
                            }
                            return new __VALUE__((int)this.__floatVal ^ Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                if(Double.parseDouble(o.__stringVal) - (int)Double.parseDouble(o.__stringVal) != 0
                                        || this.__floatVal - (int)this.__floatVal != 0) {
                                    throw new UnsupportedOperationException("Error: number has no integer representation");
                                }
                                return new __VALUE__((int)this.__floatVal ^ (int)Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__bxor");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __xor__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) ^ o.__intVal);
                        } catch (NumberFormatException e) {
                            try {
                                if(Double.parseDouble(__stringVal) - (int)Double.parseDouble(__stringVal) != 0) {
                                    throw new UnsupportedOperationException("Error: number has no integer representation");
                                }
                                return new __VALUE__((int)Double.parseDouble(__stringVal) ^ o.__intVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            if(o.__floatVal - (int)o.__floatVal != 0) {
                                throw new UnsupportedOperationException("Error: number has no integer representation");
                            }
                            return new __VALUE__(Integer.parseInt(__stringVal) ^ (int)o.__floatVal);
                        } catch (NumberFormatException e) {
                            try {
                                if(o.__floatVal - (int)o.__floatVal != 0
                                        || Double.parseDouble(__stringVal) - (int)Double.parseDouble(__stringVal) != 0) {
                                    throw new UnsupportedOperationException("Error: number has no integer representation");
                                }
                                return new __VALUE__((int)Double.parseDouble(__stringVal) ^ (int)o.__floatVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) ^ Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e0) {
                            try {
                                if(Double.parseDouble(__stringVal) - (int)Double.parseDouble(__stringVal) != 0) {
                                    throw new UnsupportedOperationException("Error: number has no integer representation");
                                }
                                return new __VALUE__((int)Double.parseDouble(__stringVal) ^ Integer.parseInt(o.__stringVal));
                            } catch (NumberFormatException e1) {
                                try {
                                    if(Double.parseDouble(o.__stringVal) - (int)Double.parseDouble(o.__stringVal) != 0) {
                                        throw new UnsupportedOperationException("Error: number has no integer representation");
                                    }
                                    return new __VALUE__(Integer.parseInt(__stringVal) ^ (int)Double.parseDouble(o.__stringVal));
                                } catch (NumberFormatException e2) {
                                    try {
                                        if(Double.parseDouble(o.__stringVal) - (int)Double.parseDouble(o.__stringVal) != 0
                                                || Double.parseDouble(__stringVal) - (int)Double.parseDouble(__stringVal) != 0) {
                                            throw new UnsupportedOperationException("Error: number has no integer representation");
                                        }
                                        return new __VALUE__((int)Double.parseDouble(__stringVal) ^ (int)Double.parseDouble(o.__stringVal));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__bxor");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __xor__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__bxor"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__bxor")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__bxor"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__bxor")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__bxor"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__bxor")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__bxor"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__bxor")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__xor__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__xor__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to xor a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __mod__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__mod");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal % o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__intVal % o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__intVal % Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__intVal % Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__mod");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __mod__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__floatVal % o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__floatVal % o.__floatVal);
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(__floatVal % Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__floatVal % Double.parseDouble(o.__stringVal));
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__mod");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __mod__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) % o.__intVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) % o.__intVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) % o.__floatVal);
                        } catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) % o.__floatVal);
                            } catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) % Integer.parseInt(o.__stringVal));
                        } catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) % Integer.parseInt(o.__stringVal));
                            } catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__(Integer.parseInt(__stringVal) % Double.parseDouble(o.__stringVal));
                                } catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__(Double.parseDouble(__stringVal) % Double.parseDouble(o.__stringVal));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__mod");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __mod__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mod"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mod")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mod"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mod")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mod"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mod")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__mod"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__mod")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__mod__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__mod__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to mod a " + this.__type + " with a " + o.__type);
    }

    public static __VALUE__ xpcall(__VALUE__ func, __VALUE__ errorFunc, ArrayList<__VALUE__> args) {
        __VALUE__ result;
        try {
            result = func.__invoke__(args);
        } catch (Exception e) {
            ArrayList<__VALUE__> parameter = new ArrayList<>();
            parameter.add(new __VALUE__(e.getMessage()));
            try {
                __VALUE__ resultError = errorFunc.__invoke__(parameter);
                if (resultError.__type == __TYPE__.VOID) {
                    resultError.__type = __TYPE__.NIL;
                }
                List<__VALUE__> listParameters = Arrays.asList(new __VALUE__(false), resultError);
                return new __VALUE__(listParameters);
            } catch (Exception ee) {
                return new __VALUE__(List.of(new __VALUE__(false), new __VALUE__("error in error handling")));
            }
        }
        List<__VALUE__> listParameters;
        if (result.__type == __TYPE__.VOID) {
            listParameters = new ArrayList<>();
            listParameters.add(new __VALUE__(true));
            return new __VALUE__(listParameters);
        }
        listParameters = Arrays.asList(new __VALUE__(true), result);
        return new __VALUE__(listParameters);
    }

    public __VALUE__ __less__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        __VALUE__ res = this.metatableFunc(o, "__lt");
                        if (res.__type == __TYPE__.NIL
                                || res.__type == __TYPE__.VOID
                                || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                            return new __VALUE__(false);
                        } else {
                            return new __VALUE__(true);
                        }
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }

            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal < o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__intVal < o.__floatVal);
                    }
                    case TABLE -> {
                        try {
                            __VALUE__ res = this.metatableFunc(o, "__lt");
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __less__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__floatVal < o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__floatVal < o.__floatVal);
                    }
                    case TABLE -> {
                        try {
                            __VALUE__ res = this.metatableFunc(o, "__lt");
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __less__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case STRING -> {
                        int compared = __stringVal.compareTo(o.__stringVal);
                        return new __VALUE__(compared < 0);
                    }
                    case TABLE -> {
                        try {
                            __VALUE__ res = this.metatableFunc(o, "__lt");
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __less__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__lt"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__lt")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__lt"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__lt")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__lt"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__lt")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__lt"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__lt")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__less__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__less__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __less_or_eql__(__VALUE__ o) {
        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        __VALUE__ res = this.metatableFunc(o, "__le");
                        if(res.__type == __TYPE__.NIL
                                || res.__type == __TYPE__.VOID
                                || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                            return new __VALUE__(false);
                        } else {
                            return new __VALUE__(true);
                        }
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case TABLE -> {
                        try {
                            __VALUE__ res = this.metatableFunc(o, "__le");
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case TABLE -> {
                        try {
                            __VALUE__ res = this.metatableFunc(o, "__le");
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case TABLE -> {
                        try {
                            __VALUE__ res = this.metatableFunc(o, "__le");
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__le"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__le")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res =  res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__le"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__le")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__le"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__le")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__le"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__le")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__less_or_eql__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__less_or_eql__(o.__seqVal.get(0));
                    }
                }
            }
        }
        try {
            return new __VALUE__(!o.__less__(this).__boolVal);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __greater__(__VALUE__ o) {
        try {
            return new __VALUE__(!__less_or_eql__(o).__boolVal);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __greater_or_eql__(__VALUE__ o) {
        try {
            return new __VALUE__(!__less__(o).__boolVal);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __eql__(__VALUE__ o) {

        switch (__type) {
            case NIL -> {
                switch (o.__type) {
                    case NIL -> {
                        return new __VALUE__(true);
                    }
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                    case INTEGER, FLOAT, BOOL, STRING, TABLE, FUNC -> {
                        return new __VALUE__(false);
                    }
                }
            }
            case BOOL -> {
                switch (o.__type) {
                    case BOOL -> {
                        return new __VALUE__(__boolVal == o.__boolVal);
                    }
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                    case INTEGER, FLOAT, NIL, VOID, STRING, TABLE, FUNC -> {
                        return new __VALUE__(false);
                    }
                }
            }
            case FUNC -> {
                switch (o.__type) {
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                    case FUNC -> {
                        return new __VALUE__(this == o);
                    }
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, TABLE -> {
                        return new __VALUE__(false);
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal == o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__intVal == o.__floatVal);
                    }
                    case BOOL, NIL, VOID, STRING, TABLE, FUNC -> {
                        return new __VALUE__(false);
                    }
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__floatVal == o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__floatVal == o.__floatVal);
                    }
                    case BOOL, NIL, VOID, STRING, TABLE, FUNC -> {
                        return new __VALUE__(false);
                    }
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, TABLE, FUNC -> {
                        return new __VALUE__(false);
                    }
                    case STRING -> {
                        return new __VALUE__(__stringVal.equals(o.__stringVal));
                    }
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                    case TABLE -> {
                        if(this == o) {
                            return new __VALUE__(true);
                        }
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__eq"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__eq")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__eq"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__eq")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                res = res.__seqVal.get(0);
                            if(res.__type == __TYPE__.NIL
                                    || res.__type == __TYPE__.VOID
                                    || res.__type == __TYPE__.BOOL && !res.__boolVal) {
                                return new __VALUE__(false);
                            } else {
                                return new __VALUE__(true);
                            }
                        }
                        return new __VALUE__(false);
                    }
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        return new __VALUE__(false);
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE, NIL, VOID, BOOL, FUNC -> {
                        return __seqVal.get(0).__eql__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__eql__(o.__seqVal.get(0));
                    }
                }
            }
        }


        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __not_eql__(__VALUE__ o) {
        try {
            return new __VALUE__(!__eql__(o).__boolVal);
        } catch(UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __logic_and__(__VALUE__ o) {
        switch (__type) {
            case NIL -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, TABLE, FUNC -> {
                        return new __VALUE__();
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case NIL -> {
                        return new __VALUE__();
                    }
                    case FUNC -> {
                        return new __VALUE__(o.__funVal);
                    }
                    case INTEGER -> {
                        return new __VALUE__(o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(o.__floatVal);
                    }
                    case BOOL -> {
                        return new __VALUE__(o.__boolVal);
                    }
                    case STRING -> {
                        return new __VALUE__(o.__stringVal);
                    }
                    case TABLE -> {
                        return new __VALUE__(o.__tableVal);
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case NIL -> {
                        return new __VALUE__();
                    }
                    case FUNC -> {
                        return new __VALUE__(o.__funVal);
                    }
                    case INTEGER -> {
                        return new __VALUE__(o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(o.__floatVal);
                    }
                    case BOOL -> {
                        return new __VALUE__(o.__boolVal);
                    }
                    case STRING -> {
                        return new __VALUE__(o.__stringVal);
                    }
                    case TABLE -> {
                        return new __VALUE__(o.__tableVal);
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case BOOL -> {
                switch (o.__type) {
                    case NIL -> {
                        return new __VALUE__();
                    }
                    case FUNC -> {
                        if(__boolVal) {
                            return new __VALUE__(o.__funVal);
                        } else {
                            return new __VALUE__(__boolVal);
                        }
                    }
                    case INTEGER -> {
                        if(__boolVal) {
                            return new __VALUE__(o.__intVal);
                        } else {
                            return new __VALUE__(__boolVal);
                        }
                    }
                    case FLOAT -> {
                        if(__boolVal) {
                            return new __VALUE__(o.__floatVal);
                        } else {
                            return new __VALUE__(__boolVal);
                        }
                    }
                    case BOOL -> {
                        return new __VALUE__(__boolVal && o.__boolVal);
                    }
                    case STRING -> {
                        if(__boolVal) {
                            return new __VALUE__(o.__stringVal);
                        } else {
                            return new __VALUE__(__boolVal);
                        }
                    }
                    case TABLE -> {
                        if(__boolVal) {
                            return new __VALUE__(o.__tableVal);
                        } else {
                            return new __VALUE__(__boolVal);
                        }
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case NIL -> {
                        return new __VALUE__();
                    }
                    case FUNC -> {
                        return new __VALUE__(o.__funVal);
                    }
                    case INTEGER -> {
                        return new __VALUE__(o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(o.__floatVal);
                    }
                    case BOOL -> {
                        return new __VALUE__(o.__boolVal);
                    }
                    case STRING -> {
                        return new __VALUE__(o.__stringVal);
                    }
                    case TABLE -> {
                        return new __VALUE__(o.__tableVal);
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL -> {
                        return new __VALUE__();
                    }
                    case FUNC -> {
                        return new __VALUE__(o.__funVal);
                    }
                    case INTEGER -> {
                        return new __VALUE__(o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(o.__floatVal);
                    }
                    case BOOL -> {
                        return new __VALUE__(o.__boolVal);
                    }
                    case STRING -> {
                        return new __VALUE__(o.__stringVal);
                    }
                    case TABLE -> {
                        return new __VALUE__(o.__tableVal);
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE, NIL, VOID, BOOL, FUNC -> {
                        return __seqVal.get(0).__logic_and__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case FUNC -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, TABLE, FUNC -> {
                        return new __VALUE__();
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __logic_or__(__VALUE__ o) {
        switch (__type) {
            case NIL -> {
                switch (o.__type) {
                    case NIL -> {
                        return new __VALUE__();
                    }
                    case INTEGER -> {
                        return new __VALUE__(o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(o.__floatVal);
                    }
                    case BOOL -> {
                        return new __VALUE__(o.__boolVal);
                    }
                    case STRING -> {
                        return new __VALUE__(o.__stringVal);
                    }
                    case TABLE -> {
                        return new __VALUE__(o.__tableVal);
                    }
                    case FUNC -> {
                        return new __VALUE__(o.__funVal);
                    }
                    case SEQ -> {
                        return __logic_or__(o.__seqVal.get(0));
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case NIL, VOID, FUNC, INTEGER, FLOAT, BOOL, STRING, TABLE, SEQ -> {
                        return new __VALUE__(this.__intVal);
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case NIL, VOID, FUNC, INTEGER, FLOAT, BOOL, STRING, TABLE, SEQ -> {
                        return new __VALUE__(this.__floatVal);
                    }
                }
            }
            case BOOL -> {
                switch (o.__type) {
                    case NIL, VOID, FUNC -> {
                        if(__boolVal) {
                            return new __VALUE__(__boolVal);
                        } else {
                            return new __VALUE__();
                        }
                    }
                    case INTEGER -> {
                        if(__boolVal) {
                            return new __VALUE__(__boolVal);
                        } else {
                            return new __VALUE__(o.__intVal);
                        }
                    }
                    case FLOAT -> {
                        if(__boolVal) {
                            return new __VALUE__(__boolVal);
                        } else {
                            return new __VALUE__(o.__floatVal);
                        }
                    }
                    case BOOL -> {
                        return new __VALUE__(__boolVal || o.__boolVal);
                    }
                    case STRING -> {
                        if(__boolVal) {
                            return new __VALUE__(__boolVal);
                        } else {
                            return new __VALUE__(o.__stringVal);
                        }
                    }
                    case TABLE -> {
                        if(__boolVal) {
                            return new __VALUE__(__boolVal);
                        } else {
                            return new __VALUE__(o.__tableVal);
                        }
                    }
                    case SEQ -> {
                        return __logic_or__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case NIL, VOID, FUNC, INTEGER, FLOAT, BOOL, STRING, TABLE, SEQ -> {
                        return new __VALUE__(this.__stringVal);
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, FUNC, INTEGER, FLOAT, BOOL, STRING, TABLE, SEQ -> {
                        return new __VALUE__(this.__tableVal);
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE, NIL, VOID, BOOL, FUNC -> {
                        return __seqVal.get(0).__logic_or__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__logic_or__(o.__seqVal.get(0));
                    }
                }
            }
            case FUNC -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, TABLE, FUNC, SEQ -> {
                        return new __VALUE__(this.__funVal);
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __unary_minus__() {

        switch (__type) {
            case INTEGER -> {
                return new __VALUE__((this.__intVal * -1));
            }
            case FLOAT -> {
                return new __VALUE__((this.__floatVal * -1));
            }
            case SEQ -> {
                return __seqVal.get(0).__unary_minus__();
            }
        }

        throw new UnsupportedOperationException("Error: attempt to unm a " + this.__type);
    }

    public __VALUE__ __not__() {

        switch (__type) {
            case NIL -> {
                return new __VALUE__(true);
            }
            case BOOL -> {
                return new __VALUE__(!this.__boolVal);
            }
            case SEQ -> {
                return __seqVal.get(0).__unary_minus__();
            }
        }
        return new __VALUE__(false);
    }

    public __VALUE__ __len__() {

        switch (__type) {
            case STRING -> {
                return new __VALUE__(this.__stringVal.length());
            }
            case TABLE -> {
                if (__metatable != null && __metatable.containsKey(new __VALUE__("__len"))) {
                    __VALUE__ res = __metatable.get(new __VALUE__("__len")).__invoke__(new ArrayList<>(List.of(this, this)));
                    if (res.__type == __TYPE__.SEQ)
                        return res.__seqVal.get(0);
                    else
                        return res;
                }
                return new __VALUE__(this.__tableVal.size());
            }
            case SEQ -> {
                return __seqVal.get(0).__len__();
            }
        }

        throw new UnsupportedOperationException("Error: attempt to get length of a " + this.__type + " value");
    }

    private int lastIntKey = 0;

    public void __append__(__VALUE__ key, __VALUE__ value) {
        if(__type == __TYPE__.TABLE) {
            if(key.__type == __TYPE__.SEQ)
                key = key.__seqVal.get(0);

            if(__tableVal.containsKey(key)) {
                if (key.__type == __TYPE__.INTEGER
                        && key.__intVal > lastIntKey) {
                    lastIntKey = key.__intVal;
                }
                __tableVal.put(key, value);
            } else {
                if (__metatable != null && __metatable.containsKey(new __VALUE__("__newindex"))) {
                    if(__metatable.get(new __VALUE__("__newindex")).__type == __TYPE__.FUNC) {
                        __metatable.get(new __VALUE__("__newindex")).__invoke__(new ArrayList<>(List.of(this, key, value)));
                        return;
                    }
                    else if(__metatable.get(new __VALUE__("__newindex")).__type == __TYPE__.TABLE) {
                        __metatable.get(new __VALUE__("__newindex")).__append__(key, value);
                        return;
                    }
                }
                if (key.__type == __TYPE__.INTEGER
                        && key.__intVal > lastIntKey) {
                    lastIntKey = key.__intVal;
                }
                __tableVal.put(key, value);
            }
            return;
        }
        throw new UnsupportedOperationException("Error: bad argument to 'insert' (table expected, got " + this.__type + ")");
    }

    public __VALUE__ __append__(__VALUE__ value) {
        if(__type == __TYPE__.TABLE) {
            __tableVal.put(new __VALUE__(++lastIntKey), value);
            return voidVal();
        }
        throw new UnsupportedOperationException("Error: bad argument to 'insert' (table expected, got " + this.__type + ")");
    }

    public __VALUE__ __invoke__(ArrayList<__VALUE__> args) {
        if (__type == __TYPE__.FUNC) {
            return __funVal.invoke(args);
        } else if(__type == __TYPE__.TABLE) {
            if (__metatable != null && __metatable.containsKey(new __VALUE__("__call"))) {
                ArrayList<__VALUE__> parameters = new ArrayList<>();
                parameters.add(this);
                parameters.addAll(args);
                __VALUE__ res = __metatable.get(new __VALUE__("__call")).__invoke__(parameters);
                if (res.__type == __TYPE__.SEQ)
                    return res.__seqVal.get(0);
                else
                    return res;
            }
            throw new UnsupportedOperationException("Error: attempt to invoke of a table value");
        } else {
            throw new UnsupportedOperationException("Error: attempt to invoke of a " + this.__type + " value");
        }
    }

    public __VALUE__ __adjust__() {
        if (__type == __TYPE__.SEQ) {
            return __seqVal.get(0);
        }
        else {
            return this;
        }
    }

    // ?????? ?????????????????? ???????? assignment
    public __VALUE__ getFromSeq(int i) {
        if(__type == __TYPE__.SEQ) {
            if (i >= __seqVal.size()) {
                return new __VALUE__();
            }
            return __seqVal.get(i);
        } else {
            if (i == 0) {
                return this;
            }
            return new __VALUE__();
        }
    }

    // ?????? ?????????? for
    public void checkNumber() {
        if(__type == __TYPE__.INTEGER || __type == __TYPE__.FLOAT)
            return;
        if(__type == __TYPE__.STRING) {
            try {
                float f = Float.parseFloat(__stringVal);
                return;
            } catch (NumberFormatException e) {

            }
        }
        throw new IllegalArgumentException("bad 'for' argument (number expected, got " + __type + ")");
    }

    // ?????? ?????????? for
    public void isZero() {
        if(__type == __TYPE__.INTEGER) {
            if (__intVal == 0)
                throw new IllegalArgumentException("'for' step is zero");
        }
        if(__type == __TYPE__.FLOAT) {
            if (__floatVal == 0)
                throw new IllegalArgumentException("'for' step is zero");
        }
        if(__type == __TYPE__.STRING) {
            try {
                float f = Float.parseFloat(__stringVal);
                if (f == 0)
                    throw new IllegalArgumentException("'for' step is zero");
            } catch (NumberFormatException e) {

            }
        }
    }

    // ?????? ?????????? for
    public int isLessThanZero() {
        if(__type == __TYPE__.INTEGER) {
            if (__intVal < 0)
                return 1;
            return 0;
        }
        if(__type == __TYPE__.FLOAT) {
            if (__floatVal < 0)
                return 1;
            return 0;
        }
        if(__type == __TYPE__.STRING) {
            try {
                float f = Float.parseFloat(__stringVal);
                if (f < 0)
                    return 1;
                return 0;
            } catch (NumberFormatException e) {

            }
        }
        throw new RuntimeException();
    }

    // ?????? ?????????? for
    public int needJmp(__VALUE__ o) {
        switch (__type) {
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        if (__intVal <= o.__intVal)
                            return 1;
                        else
                            return 0;
                    }
                    case FLOAT -> {
                        if (__intVal <= o.__floatVal)
                            return 1;
                        else
                            return 0;
                    }
                    case STRING -> {
                        try {
                            float f2 = Float.parseFloat(o.__stringVal);
                            if (__intVal <= f2)
                                return 1;
                            else
                                return 0;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        if (__floatVal <= o.__intVal)
                            return 1;
                        else
                            return 0;
                    }
                    case FLOAT -> {
                        if (__floatVal <= o.__floatVal)
                            return 1;
                        else
                            return 0;
                    }
                    case STRING -> {
                        try {
                            float f2 = Float.parseFloat(o.__stringVal);
                            if (__floatVal <= f2)
                                return 1;
                            else
                                return 0;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }
            case STRING -> {
                try {
                    float f = Float.parseFloat(__stringVal);
                    switch (o.__type) {
                        case INTEGER -> {
                            if (f <= o.__intVal)
                                return 1;
                            else
                                return 0;
                        }
                        case FLOAT -> {
                            if (f <= o.__floatVal)
                                return 1;
                            else
                                return 0;
                        }
                        case STRING -> {
                            try {
                                float f2 = Float.parseFloat(o.__stringVal);
                                if (f <= f2)
                                    return 1;
                                else
                                    return 0;
                            } catch (NumberFormatException e) {

                            }
                        }
                    }
                } catch (NumberFormatException e) {

                }
            }
        }
        throw new RuntimeException();
    }

    public __VALUE__ getByKey(__VALUE__ key) {
        if(__type == __TYPE__.TABLE) {
            if(key.__type == __TYPE__.SEQ)
                key = key.__seqVal.get(0);

            if(__tableVal.containsKey(key)) {
                __VALUE__ value = __tableVal.get(key);
                if (value.__type == __TYPE__.SEQ)
                    return value.__seqVal.get(0);
                else
                    return value;
            }
            else {
                if (__metatable != null && __metatable.containsKey(new __VALUE__("__index"))) {
                    if(__metatable.get(new __VALUE__("__index")).__type == __TYPE__.FUNC) {
                        __VALUE__ res = __metatable.get(new __VALUE__("__index")).__invoke__(new ArrayList<>(List.of(this, key)));
                        if (res.__type == __TYPE__.SEQ)
                            return res.__seqVal.get(0);
                        else
                            return res;
                    } else if(__metatable.get(new __VALUE__("__index")).__type == __TYPE__.TABLE) {
                        __VALUE__ res = __metatable.get(new __VALUE__("__index")).getByKey(key);
                        if (res.__type == __TYPE__.SEQ)
                            return res.__seqVal.get(0);
                        else
                            return res;
                    }
                }
                return new __VALUE__();
            }
        }
        if(__type == __TYPE__.STRING) {
            return new __VALUE__();
        }
        throw new UnsupportedOperationException("Error: attempt to index of a "+this.__type+" value");
    }

    public int __to_bool__() {
        // TODO ????????????????????
        if(__type == __TYPE__.BOOL) {
            if (__boolVal)
                return 1;
            else
                return 0;
        }

        if (__type == __TYPE__.SEQ) return __seqVal.get(0).__to_bool__();

        if(__type == __TYPE__.NIL) return 0;
        return 1;
    }

    public String toSString() {
        switch (this.__type) {
            case NIL -> {
                return "Nil";
            }
            case INTEGER -> {
                return String.valueOf(__intVal);
            }
            case FLOAT -> {
                return String.valueOf(__floatVal);
            }
            case BOOL -> {
                return String.valueOf(__boolVal);
            }
            case STRING -> {
                return __stringVal;
            }
            case TABLE -> {
                return ("Table" + this).replace("__VALUE__", "");
//                //TODO ??????????????
//                for (var a : __tableVal.entrySet()) {
//                    System.out.println(a.getKey().toSString() + "->" + a.getValue().toSString());
//                }
//
//                return "";
            }
            case SEQ -> {
                StringBuilder res = new StringBuilder();

                for (__VALUE__ v: __seqVal) {
                    res.append(v.toSString());
                    res.append("\t");
                }

                return res.toString();
            }
            case FUNC -> {
                return ("Func" + this).replace("__VALUE__", "");
            }
        }
        return "";
    }

    public static __VALUE__ setmetatable(__VALUE__ t, __VALUE__ mt) {
        if(t.__type == __TYPE__.TABLE) {
            if(mt.__type == __TYPE__.TABLE || mt.__type == __TYPE__.NIL) {
                t.__metatable = mt.__tableVal;
                return t;
            }
            else {
                throw new UnsupportedOperationException("Error: bad argument #2 to 'setmetatable' (nil or table expected)");
            }
        }
        else {
            throw new UnsupportedOperationException("Error: bad argument #1 to 'setmetatable' (table expected, got "+t.__type+")");
        }
    }

    public static __VALUE__ print(ArrayList<__VALUE__> value) {
        StringBuilder res = new StringBuilder();
        for (var v: value) {
            res.append(v.toSString()).append("\t");
        }

        System.out.println(res);
        return voidVal();
    }

    public static __VALUE__ read() {
        Scanner scanner = new Scanner(System.in);
        var str = scanner.nextLine();
        return new __VALUE__(str);
    }

    public static void error(__VALUE__ message) throws Exception {
        throw new Exception(message.toSString());
    }

    public static __VALUE__ __assert__(__VALUE__ condition, __VALUE__ message) throws Exception {
        if(condition.__type == __TYPE__.NIL || condition.__type == __TYPE__.BOOL && !condition.__boolVal) {
            __VALUE__.error(message);
        }
        List<__VALUE__> listParameters = Arrays.asList(condition, message);
        return new __VALUE__(listParameters);
    }

    public static __VALUE__ pcall(__VALUE__ func, ArrayList<__VALUE__> args) {
        __VALUE__ result;
        try {
            result = func.__invoke__(args);
        } catch (Exception e) {
            List<__VALUE__> listParameters = Arrays.asList(new __VALUE__(false), new __VALUE__(e.getMessage()));
            return new __VALUE__(listParameters);
        }
        List<__VALUE__> listParameters;
        if (result.__type == __TYPE__.VOID) {
            listParameters = new ArrayList<>();
            listParameters.add(new __VALUE__(true));
            return new __VALUE__(listParameters);
        }
        listParameters = Arrays.asList(new __VALUE__(true), result);
        return new __VALUE__(listParameters);
    }

    public __VALUE__ __concat__(__VALUE__ o) {

        switch (__type) {
            case NIL, VOID, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        return this.metatableFunc(o, "__concat");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }

            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(String.valueOf(__intVal) + o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(String.valueOf(__intVal) + o.__floatVal);
                    }
                    case STRING -> {
                        return new __VALUE__(__intVal + o.__stringVal);
                    }
                    case TABLE -> {
                        try {
                            this.metatableFunc(o, "__concat");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __concat__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(String.valueOf(__floatVal) + o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(String.valueOf(__floatVal) + o.__floatVal);
                    }
                    case STRING -> {
                        return new __VALUE__(__floatVal + o.__stringVal);
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__concat");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __concat__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__stringVal + o.__intVal);
                    }
                    case FLOAT -> {
                        return new __VALUE__(__stringVal + o.__floatVal);
                    }
                    case STRING -> {
                        return new __VALUE__(__stringVal + o.__stringVal);
                    }
                    case TABLE -> {
                        try {
                            return this.metatableFunc(o, "__concat");
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                    case SEQ -> {
                        return __concat__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, VOID, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__concat"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__concat")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case SEQ -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__concat"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__concat")).__invoke__(new ArrayList<>(List.of(this, o.__seqVal.get(0))));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__concat"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__concat")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__concat"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__concat")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE -> {
                        return __seqVal.get(0).__concat__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__concat__(o.__seqVal.get(0));
                    }
                }
            }
        }


        throw new UnsupportedOperationException("Error: attempt to concatenate a " + this.__type + " with a " + o.__type);
    }

    private __VALUE__ metatableFunc(__VALUE__ o, String nameFunction) {
        if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__(nameFunction))) {
            __VALUE__ res = o.__metatable.get(new __VALUE__(nameFunction)).__invoke__(new ArrayList<>(List.of(this, o)));
            if (res.__type == __TYPE__.SEQ)
                return res.__seqVal.get(0);
            else
                return res;
        }
        throw new UnsupportedOperationException("Error: attempt to perform arithmetic on a table value");
    }
}
