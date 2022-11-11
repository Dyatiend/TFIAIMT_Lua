import com.google.common.collect.Table;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
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
                case NIL ->  { return "Nil"; }
                case INTEGER -> { return "Int"; }
                case FLOAT -> { return "Float"; }
                case BOOL -> { return "Bool"; }
                case STRING -> { return "String"; }
                case TABLE -> { return "Table"; }
                case SEQ -> { return "Seq"; }
                case FUNC -> { return "Func"; }
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
            case NIL -> {
                if (o.__type == __TYPE__.TABLE) {
                    if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                        __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                        if(res.__type == __TYPE__.SEQ)
                            return res.__seqVal.get(0);
                        else
                            return res;
                    }
                }
            }
            case BOOL -> {
                if (o.__type == __TYPE__.TABLE) {
                    if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                        __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                        if(res.__type == __TYPE__.SEQ)
                            return res.__seqVal.get(0);
                        else
                            return res;
                    }
                }
            }
            case FUNC -> {
                if (o.__type == __TYPE__.TABLE) {
                    if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                        __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                        if(res.__type == __TYPE__.SEQ)
                            return res.__seqVal.get(0);
                        else
                            return res;
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
                        }
                        catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__intVal + Double.parseDouble(o.__stringVal));
                            }
                            catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                            if(res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
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
                        }
                        catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(__floatVal + Double.parseDouble(o.__stringVal));
                            }
                            catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case TABLE -> {
                        if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                            if(res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
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
                        }
                        catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) + o.__intVal);
                            }
                            catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case FLOAT -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) + o.__floatVal);
                        }
                        catch (NumberFormatException e) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) + o.__floatVal);
                            }
                            catch (NumberFormatException ignored) {

                            }
                        }
                    }
                    case STRING -> {
                        try {
                            return new __VALUE__(Integer.parseInt(__stringVal) + Integer.parseInt(o.__stringVal));
                        }
                        catch (NumberFormatException e0) {
                            try {
                                return new __VALUE__(Double.parseDouble(__stringVal) + Integer.parseInt(o.__stringVal));
                            }
                            catch (NumberFormatException e1) {
                                try {
                                    return new __VALUE__(Integer.parseInt(__stringVal) + Double.parseDouble(o.__stringVal));
                                }
                                catch (NumberFormatException e2) {
                                    try {
                                        return new __VALUE__(Double.parseDouble(__stringVal) + Double.parseDouble(o.__stringVal));
                                    }
                                    catch (NumberFormatException ignored) {

                                    }
                                }
                            }
                        }
                    }
                    case TABLE -> {
                        if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                            if(res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
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
                            __VALUE__ res = __metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                            if(res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                    }
                    case TABLE -> {
                        if (__metatable != null && __metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = __metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                            if(res.__type == __TYPE__.SEQ)
                                return res.__seqVal.get(0);
                            else
                                return res;
                        }
                        else if (o.__metatable != null && o.__metatable.containsKey(new __VALUE__("__add"))) {
                            __VALUE__ res = o.__metatable.get(new __VALUE__("__add")).__invoke__(this, o);
                            if(res.__type == __TYPE__.SEQ)
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
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal - o.__iVal);
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__iVal - o.__fVal);
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__fVal - o.__iVal);
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal - o.__fVal);
        }

        throw new UnsupportedOperationException("Error: attempt to sub a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __mul__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal * o.__iVal);
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__iVal * o.__fVal);
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__fVal * o.__iVal);
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal * o.__fVal);
        }

        throw new UnsupportedOperationException("Error: attempt to mul a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __div__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__((double)this.__iVal / o.__iVal);
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__iVal / o.__fVal);
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__fVal / o.__iVal);
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal / o.__fVal);
        }

        throw new UnsupportedOperationException("Error: attempt to div a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __floor_div__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal / o.__iVal);
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__((int) (this.__iVal / o.__fVal));
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__((int)(this.__fVal / o.__iVal));
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__((int)(this.__fVal / o.__fVal));
        }

        throw new UnsupportedOperationException("Error: attempt to idiv a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __pow__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__((int)Math.pow(this.__iVal, o.__iVal));
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__((float) Math.pow(this.__iVal, o.__iVal));
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__((float) Math.pow(this.__iVal, o.__iVal));
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__((float) Math.pow(this.__fVal, o.__fVal));
        }

        throw new UnsupportedOperationException("Error: attempt to pow a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __xor__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal ^ o.__iVal);
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            if(o.__fVal - (int)o.__fVal != 0) {
                throw new UnsupportedOperationException("Error: number has no integer representation");
            }
            return new __VALUE__(this.__iVal ^ (int)o.__fVal);
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            if(this.__fVal - (int)this.__fVal != 0) {
                throw new UnsupportedOperationException("Error: number has no integer representation");
            }
            return new __VALUE__((int)this.__fVal ^ o.__iVal);
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            if(o.__fVal - (int)o.__fVal != 0 || this.__fVal - (int)this.__fVal != 0) {
                throw new UnsupportedOperationException("Error: number has no integer representation");
            }

            return new __VALUE__((int)this.__fVal ^ (int)o.__fVal);
        }

        throw new UnsupportedOperationException("Error: attempt to xor a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __mod__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal % o.__iVal);
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__iVal % o.__fVal);
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__fVal % o.__iVal);
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal % o.__fVal);
        }

        throw new UnsupportedOperationException("Error: attempt to mod a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __concat__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.toString() + o.toString());
        }
        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.toString() + o.toString());
        }
        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.toString() + o.toString());
        }
        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.toString() + o.toString());
        }

        if (this.__type == STRING && o.__type == STRING) {
            return new __VALUE__(this.toString() + o.toString());
        }
        if (this.__type == FLOAT && o.__type == STRING) {
            return new __VALUE__(this.toString() + o.toString());
        }
        if (this.__type == INTEGER && o.__type == STRING) {
            return new __VALUE__(this.toString() + o.toString());
        }
        if (this.__type == STRING && o.__type == FLOAT) {
            return new __VALUE__(this.toString() + o.toString());
        }
        if (this.__type == STRING && o.__type == INTEGER) {
            return new __VALUE__(this.toString() + o.toString());
        }

        throw new UnsupportedOperationException("Error: attempt to concatenate a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __less__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal < o.__iVal);
        }
        //TODO ПРОВЕРИТЬ СРАВНЕНИЕ ФЛОАТОВ
        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__iVal < o.__fVal);
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__fVal < o.__iVal);
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal < o.__fVal);
        }

        if (this.__type == STRING && o.__type == STRING) {
            int compared = this.__sVal.compareTo(o.__sVal);
            return new __VALUE__(compared < 0);
        }

        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __less_or_eql__(__VALUE__ o) {
        try {
            return new __VALUE__(__eql__(o).__bVal || __less__(o).__bVal);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __greater__(__VALUE__ o) {
        try {
            return new __VALUE__(!__less_or_eql__(o).__bVal);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __greater_or_eql__(__VALUE__ o) {
        try {
            return new __VALUE__(!__less__(o).__bVal);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __eql__(__VALUE__ o) {
        if (this.__type == NIL && o.__type == NIL) return new __VALUE__(true);
        if (this.__type == NIL || o.__type == NIL) return new __VALUE__(false);
        if (__type == FUNC || o.__type == FUNC) return new __VALUE__(
                __type == o.__type || __fun == o.__fun
        );

        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal == o.__iVal);
        }
        //TODO ПРОВЕРИТЬ СРАВНЕНИЕ ФЛОАТОВ
        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__iVal == o.__fVal);
        }
        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__fVal == o.__iVal);
        }
        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal == o.__fVal);
        }

        if (this.__type == STRING && o.__type == STRING) {
            return new __VALUE__(this.__sVal.equals(o.__sVal));
        }

        if (this.__type == BOOLEAN && o.__type == BOOLEAN) {
            return new __VALUE__(this.__bVal == o.__bVal);
        }

        if (this.__type != o.__type) return new __VALUE__(false);

        //TODO Две таблицы сравнивать на память КАК??

        throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __not_eql__(__VALUE__ o) {
        try {
            return new __VALUE__(!__eql__(o).__bVal);
        } catch(UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Error: attempt to compare a " + this.__type + " with a " + o.__type);
        }
    }

    public __VALUE__ __logic_and__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(o.__iVal);
        }
        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(o.__fVal);
        }
        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(o.__iVal);
        }
        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(o.__fVal);
        }

        if (this.__type == STRING && o.__type == STRING) {
            return new __VALUE__(o.__sVal);
        }
        if (this.__type == INTEGER && o.__type == STRING) {
            return new __VALUE__(o.__sVal);
        }
        if (this.__type == FLOAT && o.__type == STRING) {
            return new __VALUE__(o.__sVal);
        }
        if (this.__type == STRING && o.__type == INTEGER) {
            return new __VALUE__(o.__iVal);
        }
        if (this.__type == STRING && o.__type == FLOAT) {
            return new __VALUE__(o.__fVal);
        }

        if (this.__type == STRING && o.__type == TABLE) {
            return new __VALUE__(o.__aVal);
        }
        if (this.__type == INTEGER && o.__type == TABLE) {
            return new __VALUE__(o.__aVal);
        }
        if (this.__type == FLOAT && o.__type == TABLE) {
            return new __VALUE__(o.__aVal);
        }
        if (this.__type == TABLE && o.__type == INTEGER) {
            return new __VALUE__(o.__iVal);
        }
        if (this.__type == TABLE && o.__type == FLOAT) {
            return new __VALUE__(o.__fVal);
        }
        if (this.__type == TABLE && o.__type == STRING) {
            return new __VALUE__(o.__sVal);
        }
        if (this.__type == TABLE && o.__type == TABLE) {
            return new __VALUE__(o.__aVal);
        }

        if (this.__type == BOOLEAN && o.__type == BOOLEAN) {
            return new __VALUE__(this.__bVal && o.__bVal);
        }

        if (this.__type == BOOLEAN && o.__type == INTEGER) {
            if(this.__bVal) {
                return new __VALUE__(o.__iVal);
            } else {
                return new __VALUE__(this.__bVal);
            }
        }
        if (this.__type == BOOLEAN && o.__type == FLOAT) {
            if(this.__bVal) {
                return new __VALUE__(o.__fVal);
            } else {
                return new __VALUE__(this.__bVal);
            }
        }
        if (this.__type == BOOLEAN && o.__type == STRING) {
            if(this.__bVal) {
                return new __VALUE__(o.__sVal);
            } else {
                return new __VALUE__(this.__bVal);
            }
        }
        if (this.__type == BOOLEAN && o.__type == TABLE) {
            return new __VALUE__(o.__aVal);
        }

        if (o.__type == BOOLEAN) {
            return new __VALUE__(o.__bVal);
        }

        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __logic_or__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal);
        }
        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal);
        }
        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal);
        }
        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal);
        }

        if (this.__type == STRING && o.__type == STRING) {
            return new __VALUE__(this.__sVal);
        }
        if (this.__type == INTEGER && o.__type == STRING) {
            return new __VALUE__(this.__sVal);
        }
        if (this.__type == FLOAT && o.__type == STRING) {
            return new __VALUE__(this.__sVal);
        }
        if (this.__type == STRING && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal);
        }
        if (this.__type == STRING && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal);
        }

        if (this.__type == STRING && o.__type == TABLE) {
            return new __VALUE__(this.__sVal);
        }
        if (this.__type == INTEGER && o.__type == TABLE) {
            return new __VALUE__(this.__iVal);
        }
        if (this.__type == FLOAT && o.__type == TABLE) {
            return new __VALUE__(this.__fVal);
        }
        if (this.__type == TABLE && o.__type == INTEGER) {
            return new __VALUE__(this.__aVal);
        }
        if (this.__type == TABLE && o.__type == FLOAT) {
            return new __VALUE__(this.__aVal);
        }
        if (this.__type == TABLE && o.__type == STRING) {
            return new __VALUE__(this.__aVal);
        }
        if (this.__type == TABLE && o.__type == TABLE) {
            return new __VALUE__(this.__aVal);
        }

        if (this.__type == BOOLEAN && o.__type == BOOLEAN) {
            return new __VALUE__(this.__bVal || o.__bVal);
        }

        if (this.__type == BOOLEAN && o.__type == INTEGER) {
            if(this.__bVal) {
                return new __VALUE__(this.__bVal);
            } else {
                return new __VALUE__(o.__iVal);
            }
        }
        if (this.__type == BOOLEAN && o.__type == FLOAT) {
            if(this.__bVal) {
                return new __VALUE__(this.__bVal);
            } else {
                return new __VALUE__(o.__fVal);
            }
        }
        if (this.__type == BOOLEAN && o.__type == STRING) {
            if(this.__bVal) {
                return new __VALUE__(this.__bVal);
            } else {
                return new __VALUE__(o.__sVal);
            }
        }
        if (this.__type == BOOLEAN && o.__type == TABLE) {
            if(this.__bVal) {
                return new __VALUE__(this.__bVal);
            } else {
                return new __VALUE__(o.__aVal);
            }
        }

        if (this.__type == INTEGER && o.__type == BOOLEAN) {
            return new __VALUE__(this.__iVal);
        }
        if (this.__type == FLOAT && o.__type == BOOLEAN) {
            return new __VALUE__(this.__fVal);
        }
        if (this.__type == STRING && o.__type == BOOLEAN) {
            return new __VALUE__(this.__sVal);
        }
        if (this.__type == TABLE && o.__type == BOOLEAN) {
            return new __VALUE__(this.__aVal);
        }
        throw new UnsupportedOperationException("Error: attempt to or logic a " + this.__type + " with a " + o.__type);
    }

    public __VALUE__ __unary_minus__() {

        if (this.__type == INTEGER) {
            return new __VALUE__((this.__iVal * -1));
        }

        if (this.__type == FLOAT) {
            return new __VALUE__((this.__fVal * -1));
        }

        throw new UnsupportedOperationException("Error: attempt to unm a " + this.__type);
    }

    public __VALUE__ __not__() {
        if (this.__type == NIL) return new __VALUE__(true);
        if (this.__type == BOOLEAN) return new __VALUE__(!this.__bVal);
        return new __VALUE__(false);
    }

    public __VALUE__ __len__() {
        if (this.__type == STRING) {
            return new __VALUE__(this.__sVal.length());
        }

        if (this.__type == TABLE) {
            return new __VALUE__(this.__aVal.size());
        }

        throw new UnsupportedOperationException("Error: attempt to get length of a " + this.__type + " value");
    }

    public __VALUE__ __invoke__(__VALUE__... args) {
        if(__type == __TYPE__.FUNC) {
            return __funVal.invoke(args);
        }
        else {
            throw new UnsupportedOperationException("Error: attempt to invoke of a " + this.__type + " value");
        }
    }

    public String toString() {
        String s = "";
        if (this.__type == INTEGER)
            s += this.__iVal;
        if (this.__type == FLOAT)
            s += this.__fVal;
        if (this.__type == STRING)
            s += this.__sVal;
        if (this.__type == BOOLEAN)
            s += this.__bVal;
        return s;
    }

    public static void print(__VALUE__ value) {
        System.out.print(value.toString());
    }

    public static __VALUE__  __read__() {
        Scanner scanner = new Scanner(System.in);
        return new __VALUE__(scanner.nextLine());
    }

    //TODO МБ нужны __member_access__   __member_access_assign__   __to_i__   __to_f__   __to_s__    __split__   equals
}