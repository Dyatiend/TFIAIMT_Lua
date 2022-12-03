import com.google.common.collect.Table;

import java.util.*;

public class __VALUE__ {

    public enum __TYPE__ {
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

    // +++++++++++++++++++++++++++ Методы +++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public __VALUE__ __add__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__add");
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
                            this.name(o, "__add");
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
                            this.name(o, "__add");
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
                            this.name(o, "__add");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__add")).__invoke__(new ArrayList<>(List.of(this, o)));
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


//        if (this.__type == __TYPE__.INTEGER && o.__type == __TYPE__.INTEGER) {
//            return new __VALUE__(this.__iVal + o.__iVal);
//        }
//
//        if (this.__type == __TYPE__.INTEGER && o.__type == __TYPE__.FLOAT) {
//            return new __VALUE__(this.__iVal + o.__fVal);
//        }
//
//        if (this.__type == __TYPE__.FLOAT && o.__type == __TYPE__.INTEGER) {
//            return new __VALUE__(this.__fVal + o.__iVal);
//        }
//
//        if (this.__type == __TYPE__.FLOAT && o.__type == __TYPE__.FLOAT) {
//            return new __VALUE__(this.__fVal + o.__fVal);
//        }
//
//        if (__type == __TYPE__.STRING && o.__type == __TYPE__.STRING) {
//            try {
//                return new __VALUE__(Integer.parseInt(__sVal) + Integer.parseInt(o.__sVal));
//            }
//            catch (NumberFormatException e) {
//                throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
//            }
//        }
//
//        if (__type == __TYPE__.STRING && o.__type == __TYPE__.INTEGER) {
//            try {
//                return new __VALUE__(Integer.parseInt(__sVal) + o.__iVal);
//            }
//            catch (NumberFormatException e) {
//                throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
//            }
//        }
//
//        if (__type == __TYPE__.STRING && o.__type == __TYPE__.FLOAT) {
//            try {
//                return new __VALUE__(Integer.parseInt(__sVal) + o.__fVal);
//            }
//            catch (NumberFormatException e) {
//                throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
//            }
//        }
//
//        if (__type == __TYPE__.INTEGER && o.__type == __TYPE__.STRING) {
//            try {
//                return new __VALUE__(__iVal + Integer.parseInt(o.__sVal));
//            }
//            catch (NumberFormatException e) {
//                throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
//            }
//        }
//
//        if (__type == __TYPE__.FLOAT && o.__type == __TYPE__.STRING) {
//            try {
//                return new __VALUE__(__fVal + Integer.parseInt(o.__sVal));
//            }
//            catch (NumberFormatException e) {
//                throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
//            }
//        }
//
//
//                Table
//
//                VarArg
//
//        throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __sub__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__sub");
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
                            this.name(o, "__sub");
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
                            this.name(o, "__sub");
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
                            this.name(o, "__sub");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__sub"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__sub")).__invoke__(new ArrayList<>(List.of(this, o)));
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


//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal - o.__iVal);
//        }
//
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.__iVal - o.__fVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.__fVal - o.__iVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal - o.__fVal);
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to sub a " + this.__type + " with a " + o.__type);
    }


    public __VALUE__ __mul__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__mul");
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
                            this.name(o, "__mul");
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
                            this.name(o, "__mul");
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
                            this.name(o, "__mul");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mul"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mul")).__invoke__(new ArrayList<>(List.of(this, o)));
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
//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal * o.__iVal);
//        }
//
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.__iVal * o.__fVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.__fVal * o.__iVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal * o.__fVal);
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to mul a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __div__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__div");
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
                            this.name(o, "__div");
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
                            this.name(o, "__div");
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
                            this.name(o, "__div");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__div"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__div")).__invoke__(new ArrayList<>(List.of(this, o)));
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

//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__((double)this.__iVal / o.__iVal);
//        }
//
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.__iVal / o.__fVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.__fVal / o.__iVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal / o.__fVal);
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to div a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __floor_div__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__idiv");
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
                            this.name(o, "__idiv");
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
                            this.name(o, "__idiv");
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
                            this.name(o, "__idiv");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__idiv"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__idiv")).__invoke__(new ArrayList<>(List.of(this, o)));
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

//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal / o.__iVal);
//        }
//
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__((int) (this.__iVal / o.__fVal));
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__((int)(this.__fVal / o.__iVal));
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__((int)(this.__fVal / o.__fVal));
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to idiv a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __pow__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__pow");
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
                            this.name(o, "__pow");
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
                            this.name(o, "__pow");
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
                            this.name(o, "__pow");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__pow"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__pow")).__invoke__(new ArrayList<>(List.of(this, o)));
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

//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__((int)Math.pow(this.__iVal, o.__iVal));
//        }
//
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__((float) Math.pow(this.__iVal, o.__iVal));
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__((float) Math.pow(this.__iVal, o.__iVal));
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__((float) Math.pow(this.__fVal, o.__fVal));
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to pow a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __xor__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__xor");
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
                            this.name(o, "__xor");
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
                            this.name(o, "__xor");
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
                            this.name(o, "__xor");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__xor"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__xor")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__xor"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__xor")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__xor"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__xor")).__invoke__(new ArrayList<>(List.of(this, o)));
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


//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal ^ o.__iVal);
//        }
//
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            if(o.__fVal - (int)o.__fVal != 0) {
//                throw new UnsupportedOperationException("Error: number has no integer representation");
//            }
//            return new __VALUE__(this.__iVal ^ (int)o.__fVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            if(this.__fVal - (int)this.__fVal != 0) {
//                throw new UnsupportedOperationException("Error: number has no integer representation");
//            }
//            return new __VALUE__((int)this.__fVal ^ o.__iVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            if(o.__fVal - (int)o.__fVal != 0 || this.__fVal - (int)this.__fVal != 0) {
//                throw new UnsupportedOperationException("Error: number has no integer representation");
//            }
//
//            return new __VALUE__((int)this.__fVal ^ (int)o.__fVal);
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to xor a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __mod__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__mod");
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
                            this.name(o, "__mod");
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
                            this.name(o, "__mod");
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
                            this.name(o, "__mod");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__mod"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__mod")).__invoke__(new ArrayList<>(List.of(this, o)));
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

//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal % o.__iVal);
//        }
//
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.__iVal % o.__fVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.__fVal % o.__iVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal % o.__fVal);
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to mod a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __concat__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__concat");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }

            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(String.valueOf(__intVal) + String.valueOf(o.__intVal));
                    }
                    case FLOAT -> {
                        return new __VALUE__(String.valueOf(__intVal) + String.valueOf(o.__floatVal));
                    }
                    case STRING -> {
                        return new __VALUE__(String.valueOf(__intVal) + o.__stringVal);
                    }
                    case TABLE -> {
                        try {
                            this.name(o, "__concat");
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
                        return new __VALUE__(String.valueOf(__floatVal) + String.valueOf(o.__intVal));
                    }
                    case FLOAT -> {
                        return new __VALUE__(String.valueOf(__floatVal) + String.valueOf(o.__floatVal));
                    }
                    case STRING -> {
                        return new __VALUE__(String.valueOf(__floatVal) + o.__stringVal);
                    }
                    case TABLE -> {
                        try {
                            this.name(o, "__concat");
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
                        return new __VALUE__(__stringVal + String.valueOf(o.__intVal));
                    }
                    case FLOAT -> {
                        return new __VALUE__(__stringVal + String.valueOf(o.__floatVal));
                    }
                    case STRING -> {
                        return new __VALUE__(__stringVal + o.__stringVal);
                    }
                    case TABLE -> {
                        try {
                            this.name(o, "__concat");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__concat"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__concat")).__invoke__(new ArrayList<>(List.of(this, o)));
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

//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//
//        if (this.__type == STRING && o.__type == STRING) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//        if (this.__type == FLOAT && o.__type == STRING) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//        if (this.__type == INTEGER && o.__type == STRING) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//        if (this.__type == STRING && o.__type == FLOAT) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
//        if (this.__type == STRING && o.__type == INTEGER) {
//            return new __VALUE__(this.toString() + o.toString());
//        }
    }

    public __VALUE__ __less__(__VALUE__ o) {

        switch (__type) {
            case NIL, BOOL, FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    try {
                        this.name(o, "__less");
                    } catch (UnsupportedOperationException ignored) {
                    }
                }
            }

            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal < o.__intVal);
                    }
                    case FLOAT -> { //TODO ПРОВЕРИТЬ СРАВНЕНИЕ ФЛОАТОВ
                        return new __VALUE__(__intVal < o.__floatVal);
                    }
                    case TABLE -> {
                        try {
                            this.name(o, "__less");
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
                            this.name(o, "__less");
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
                            this.name(o, "__less");
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, SEQ, FUNC -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__less"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__less")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__less"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__less")).__invoke__(new ArrayList<>(List.of(this, o)));
                            if (res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        } else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__less"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__less")).__invoke__(new ArrayList<>(List.of(this, o)));
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
                        return __seqVal.get(0).__less__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__less__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);


//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal < o.__iVal);
//        }
//        //TODO ПРОВЕРИТЬ СРАВНЕНИЕ ФЛОАТОВ
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.__iVal < o.__fVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.__fVal < o.__iVal);
//        }
//
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal < o.__fVal);
//        }
//
//        if (this.__type == STRING && o.__type == STRING) {
//            int compared = this.__sVal.compareTo(o.__sVal);
//            return new __VALUE__(compared < 0);
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __less_or_eql__(__VALUE__ o) {
        try {
            return new __VALUE__(__eql__(o).__boolVal || __less__(o).__boolVal);
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
                    case INTEGER, FLOAT, NIL, STRING, TABLE, FUNC -> {
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
                    case NIL, INTEGER, FLOAT, BOOL, STRING, TABLE -> {
                        return new __VALUE__(false);
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case INTEGER -> {
                        return new __VALUE__(__intVal == o.__intVal);
                    }
                    case FLOAT -> { //TODO ПРОВЕРИТЬ СРАВНЕНИЕ ФЛОАТОВ
                        return new __VALUE__(__intVal == o.__floatVal);
                    }
                    case BOOL, NIL, STRING, TABLE, FUNC -> {
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
                    case FLOAT -> { //TODO ПРОВЕРИТЬ СРАВНЕНИЕ ФЛОАТОВ
                        return new __VALUE__(__floatVal == o.__floatVal);
                    }
                    case BOOL, NIL, STRING, TABLE, FUNC -> {
                        return new __VALUE__(false);
                    }
                    case SEQ -> {
                        return __eql__(o.__seqVal.get(0));
                    }
                }
            }
            case STRING -> {
                switch (o.__type) {
                    case NIL, INTEGER, FLOAT, BOOL, TABLE, FUNC -> {
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
                        return new __VALUE__(this == o); //TODO Проверить
                    }
                    case NIL, INTEGER, FLOAT, BOOL, STRING, FUNC -> {
                        return new __VALUE__(false);
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE, NIL, BOOL, FUNC -> {
                        return __seqVal.get(0).__eql__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__eql__(o.__seqVal.get(0));
                    }
                }
            }
        }


        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);

//
//        if (this.__type == NIL && o.__type == NIL) return new __VALUE__(true);
//        if (this.__type == NIL || o.__type == NIL) return new __VALUE__(false);
//        if (__type == FUNC || o.__type == FUNC) return new __VALUE__(
//                __type == o.__type || __fun == o.__fun
//        );
//
//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal == o.__iVal);
//        }
//        //TODO ПРОВЕРИТЬ СРАВНЕНИЕ ФЛОАТОВ
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.__iVal == o.__fVal);
//        }
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.__fVal == o.__iVal);
//        }
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal == o.__fVal);
//        }
//
//        if (this.__type == STRING && o.__type == STRING) {
//            return new __VALUE__(this.__sVal.equals(o.__sVal));
//        }
//
//        if (this.__type == BOOLEAN && o.__type == BOOLEAN) {
//            return new __VALUE__(this.__bVal == o.__bVal);
//        }
//
//        if (this.__type != o.__type) return new __VALUE__(false);
//
//        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __not_eql__(__VALUE__ o) {
        try {
            return new __VALUE__(!__eql__(o).__boolVal);
        } catch(UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __logic_and__(__VALUE__ o) {
        //TODO При ложном первом аргументе второй не делать
        switch (__type) {
            case NIL -> {
                switch (o.__type) {
                    case NIL, INTEGER, FLOAT, BOOL, STRING, TABLE, FUNC -> {
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
                    case INTEGER, FLOAT, STRING, TABLE, NIL, BOOL, FUNC -> {
                        return __seqVal.get(0).__eql__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__eql__(o.__seqVal.get(0));
                    }
                }
            }
            case FUNC -> {
                switch (o.__type) {
                    case NIL, INTEGER, FLOAT, BOOL, STRING, TABLE, FUNC -> {
                        return new __VALUE__();
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);


//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(o.__iVal);
//        }
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(o.__fVal);
//        }
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(o.__iVal);
//        }
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(o.__fVal);
//        }
//
//        if (this.__type == STRING && o.__type == STRING) {
//            return new __VALUE__(o.__sVal);
//        }
//        if (this.__type == INTEGER && o.__type == STRING) {
//            return new __VALUE__(o.__sVal);
//        }
//        if (this.__type == FLOAT && o.__type == STRING) {
//            return new __VALUE__(o.__sVal);
//        }
//        if (this.__type == STRING && o.__type == INTEGER) {
//            return new __VALUE__(o.__iVal);
//        }
//        if (this.__type == STRING && o.__type == FLOAT) {
//            return new __VALUE__(o.__fVal);
//        }
//
//        if (this.__type == STRING && o.__type == TABLE) {
//            return new __VALUE__(o.__aVal);
//        }
//        if (this.__type == INTEGER && o.__type == TABLE) {
//            return new __VALUE__(o.__aVal);
//        }
//        if (this.__type == FLOAT && o.__type == TABLE) {
//            return new __VALUE__(o.__aVal);
//        }
//        if (this.__type == TABLE && o.__type == INTEGER) {
//            return new __VALUE__(o.__iVal);
//        }
//        if (this.__type == TABLE && o.__type == FLOAT) {
//            return new __VALUE__(o.__fVal);
//        }
//        if (this.__type == TABLE && o.__type == STRING) {
//            return new __VALUE__(o.__sVal);
//        }
//        if (this.__type == TABLE && o.__type == TABLE) {
//            return new __VALUE__(o.__aVal);
//        }
//
//        if (this.__type == BOOLEAN && o.__type == BOOLEAN) {
//            return new __VALUE__(this.__bVal && o.__bVal);
//        }
//
//        if (this.__type == BOOLEAN && o.__type == INTEGER) {
//            if(this.__bVal) {
//                return new __VALUE__(o.__iVal);
//            } else {
//                return new __VALUE__(this.__bVal);
//            }
//        }
//        if (this.__type == BOOLEAN && o.__type == FLOAT) {
//            if(this.__bVal) {
//                return new __VALUE__(o.__fVal);
//            } else {
//                return new __VALUE__(this.__bVal);
//            }
//        }
//        if (this.__type == BOOLEAN && o.__type == STRING) {
//            if(this.__bVal) {
//                return new __VALUE__(o.__sVal);
//            } else {
//                return new __VALUE__(this.__bVal);
//            }
//        }
//        if (this.__type == BOOLEAN && o.__type == TABLE) {
//            return new __VALUE__(o.__aVal);
//        }
//
//        if (o.__type == BOOLEAN) {
//            return new __VALUE__(o.__bVal);
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);
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
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case INTEGER -> {
                switch (o.__type) {
                    case NIL, FUNC -> {
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
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case FLOAT -> {
                switch (o.__type) {
                    case NIL, FUNC -> {
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
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case BOOL -> {
                switch (o.__type) {
                    case NIL, FUNC -> {
                        return new __VALUE__();
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
                    case NIL, FUNC -> {
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
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case TABLE -> {
                switch (o.__type) {
                    case NIL, FUNC -> {
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
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
            case SEQ -> {
                switch (o.__type) {
                    case INTEGER, FLOAT, STRING, TABLE, NIL, BOOL, FUNC -> {
                        return __seqVal.get(0).__eql__(o);
                    }
                    case SEQ -> {
                        return __seqVal.get(0).__eql__(o.__seqVal.get(0));
                    }
                }
            }
            case FUNC -> {
                switch (o.__type) {
                    case NIL, INTEGER, FLOAT, BOOL, STRING, TABLE, FUNC -> {
                        return new __VALUE__();
                    }
                    case SEQ -> {
                        return __logic_and__(o.__seqVal.get(0));
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);

//        if (this.__type == INTEGER && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal);
//        }
//        if (this.__type == INTEGER && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal);
//        }
//        if (this.__type == FLOAT && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal);
//        }
//        if (this.__type == FLOAT && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal);
//        }
//
//        if (this.__type == STRING && o.__type == STRING) {
//            return new __VALUE__(this.__sVal);
//        }
//        if (this.__type == INTEGER && o.__type == STRING) {
//            return new __VALUE__(this.__sVal);
//        }
//        if (this.__type == FLOAT && o.__type == STRING) {
//            return new __VALUE__(this.__sVal);
//        }
//        if (this.__type == STRING && o.__type == INTEGER) {
//            return new __VALUE__(this.__iVal);
//        }
//        if (this.__type == STRING && o.__type == FLOAT) {
//            return new __VALUE__(this.__fVal);
//        }
//
//        if (this.__type == STRING && o.__type == TABLE) {
//            return new __VALUE__(this.__sVal);
//        }
//        if (this.__type == INTEGER && o.__type == TABLE) {
//            return new __VALUE__(this.__iVal);
//        }
//        if (this.__type == FLOAT && o.__type == TABLE) {
//            return new __VALUE__(this.__fVal);
//        }
//        if (this.__type == TABLE && o.__type == INTEGER) {
//            return new __VALUE__(this.__aVal);
//        }
//        if (this.__type == TABLE && o.__type == FLOAT) {
//            return new __VALUE__(this.__aVal);
//        }
//        if (this.__type == TABLE && o.__type == STRING) {
//            return new __VALUE__(this.__aVal);
//        }
//        if (this.__type == TABLE && o.__type == TABLE) {
//            return new __VALUE__(this.__aVal);
//        }
//
//        if (this.__type == BOOLEAN && o.__type == BOOLEAN) {
//            return new __VALUE__(this.__bVal || o.__bVal);
//        }
//
//        if (this.__type == BOOLEAN && o.__type == INTEGER) {
//            if(this.__bVal) {
//                return new __VALUE__(this.__bVal);
//            } else {
//                return new __VALUE__(o.__iVal);
//            }
//        }
//        if (this.__type == BOOLEAN && o.__type == FLOAT) {
//            if(this.__bVal) {
//                return new __VALUE__(this.__bVal);
//            } else {
//                return new __VALUE__(o.__fVal);
//            }
//        }
//        if (this.__type == BOOLEAN && o.__type == STRING) {
//            if(this.__bVal) {
//                return new __VALUE__(this.__bVal);
//            } else {
//                return new __VALUE__(o.__sVal);
//            }
//        }
//        if (this.__type == BOOLEAN && o.__type == TABLE) {
//            if(this.__bVal) {
//                return new __VALUE__(this.__bVal);
//            } else {
//                return new __VALUE__(o.__aVal);
//            }
//        }
//
//        if (this.__type == INTEGER && o.__type == BOOLEAN) {
//            return new __VALUE__(this.__iVal);
//        }
//        if (this.__type == FLOAT && o.__type == BOOLEAN) {
//            return new __VALUE__(this.__fVal);
//        }
//        if (this.__type == STRING && o.__type == BOOLEAN) {
//            return new __VALUE__(this.__sVal);
//        }
//        if (this.__type == TABLE && o.__type == BOOLEAN) {
//            return new __VALUE__(this.__aVal);
//        }
//        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);
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


//        if (this.__type == __TYPE__.INTEGER) {
//            return new __VALUE__((this.__intVal * -1));
//        }
//
//        if (this.__type == __TYPE__.FLOAT) {
//            return new __VALUE__((this.__floatVal * -1));
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to unm a " + this.__type);
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


//        if (this.__type == NIL) return new __VALUE__(true);
//        if (this.__type == BOOLEAN) return new __VALUE__(!this.__bVal);
//        return new __VALUE__(false);
    }

    public __VALUE__ __len__() {

        switch (__type) {
            case STRING -> {
                return new __VALUE__(this.__stringVal.length());
            }
            case TABLE -> {
                return new __VALUE__(this.__tableVal.size());
            }
            case SEQ -> {
                return __seqVal.get(0).__len__();
            }
        }

        throw new UnsupportedOperationException("Error: attempt to get length of a " + this.__type + " value");

//        if (this.__type == STRING) {
//            return new __VALUE__(this.__sVal.length());
//        }
//
//        if (this.__type == TABLE) {
//            return new __VALUE__(this.__aVal.size());
//        }
//
//        throw new UnsupportedOperationException("Error: attempt to get length of a " + this.__type + " value");
    }

    // TODO: getByKey , exceptions, append с ключем и без

    private int lastIntKey = 0;

    public void __append__(__VALUE__ key, __VALUE__ value) {
        if (key.__type == __TYPE__.INTEGER
                && key.__intVal > lastIntKey) {
            lastIntKey = key.__intVal;
        }
        __tableVal.put(key, value);
    }

    public void __append__(__VALUE__ value) {
        if(__type == __TYPE__.TABLE) {
            __tableVal.put(new __VALUE__(++lastIntKey), value);
        }
        throw new UnsupportedOperationException("Error");
    }

    public __VALUE__ __invoke__(ArrayList<__VALUE__> args) {
        if (__type == __TYPE__.FUNC) {
            return __funVal.invoke(args);
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

    public int __to_bool__() {
        // TODO протестить
        if(__type == __TYPE__.BOOL) {
            if (__boolVal)
                return 1;
            else
                return 0;
        }

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
                //return ("Table" + this).replace("__VALUE__", "");
                //TODO Удалить
                for (var a : __tableVal.entrySet()) {
                    System.out.println(a.getKey().toSString() + "->" + a.getValue().toSString());
                }

                return "";
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

    public static void print(ArrayList<__VALUE__> value) {
        StringBuilder res = new StringBuilder();
        for (var v: value) {
            res.append(v.toSString()).append("\t");
        }

        System.out.println(res);
    }

    public static __VALUE__ read() {
        Scanner scanner = new Scanner(System.in);
        var str = scanner.nextLine();
        return new __VALUE__(str);
    }

    //TODO переименовать
    private __VALUE__ name(__VALUE__ o, String nameFunction) {
        if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__(nameFunction))) {
            __VALUE__ res = o.__metatable.get(new __VALUE__(nameFunction)).__invoke__(new ArrayList<>(List.of(this, o)));
            if (res.__type == __TYPE__.SEQ)
                return res.__seqVal.get(0);
            else
                return res;
        }
        throw new UnsupportedOperationException("");
    }
}
