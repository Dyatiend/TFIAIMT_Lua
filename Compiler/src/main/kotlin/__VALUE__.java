import java.util.ArrayList;
import java.util.Scanner;

public class __VALUE__ {
    private static final int NIL = 0;
    private static final int INTEGER = 1;
    private static final int FLOAT = 2;
    private static final int BOOLEAN = 3;
    private static final int STRING = 4;
    private static final int TABLE = 5;
    private static final int VARARG = 6;
    private static final int FUNC = 7;

    public int __iVal;
    public boolean __bVal;
    public double __fVal;
    public String __sVal;
    public ArrayList<__VALUE__> __aVal;

    public int __type = -1;

    public __VALUE__() {
        __type = NIL;
    }

    public __VALUE__(int value) {
        this.__iVal = value;
        this.__type = INTEGER;
    }

    public __VALUE__(boolean value) {
        this.__bVal = value;
        this.__type = BOOLEAN;
    }

    public __VALUE__(double value) {
        this.__fVal = value;
        this.__type = FLOAT;
    }

    public __VALUE__(String value) {
        this.__sVal = value;
        this.__type = STRING;
    }

    public __VALUE__(ArrayList<__VALUE__> value) {
        this.__aVal = new ArrayList<>();
        this.__aVal = value;
        this.__type = TABLE;
    }

    public __VALUE__ __add__(__VALUE__ o) {
        if (this.__type == INTEGER && o.__type == INTEGER) {
            return new __VALUE__(this.__iVal + o.__iVal);
        }

        if (this.__type == INTEGER && o.__type == FLOAT) {
            return new __VALUE__(this.__iVal + o.__fVal);
        }

        if (this.__type == FLOAT && o.__type == INTEGER) {
            return new __VALUE__(this.__fVal + o.__iVal);
        }

        if (this.__type == FLOAT && o.__type == FLOAT) {
            return new __VALUE__(this.__fVal + o.__fVal);
        }

        throw new UnsupportedOperationException("Error: attempt to add a " + this.__type + " with a " + o.__type);
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
            return new __VALUE__(__eql__(o).__bVal);
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
