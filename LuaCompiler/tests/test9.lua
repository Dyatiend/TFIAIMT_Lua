function fib(n)
  if n < 2 then return 1 end
  if num > 40 then
    print('over 40')
  elseif s ~= 'walternate' then  -- ~= is not equals.
    sum = {}
    sum.b = f1.b * f2.b
    sum.a = f1.a * f2.b + f2.a * f1.b
  -- Equality check is == like Python; ok for strs.
    write('not over 40\n')  -- Defaults to stdout.
  else
    -- Variables are global by default.
    thisIsGlobal = 5  -- Camel case is common.
    karlSum = 0
    for i = 1, 100 do  -- The range includes both ends.
      karlSum = karlSum + i
    end
    -- How to make a variable local:
    local line = read()  -- Reads next stdin line.

    -- String concatenation uses the .. operator:
    print('Winter is coming, ' .. line)
  end
  repeat
    print('the way of the future')
    num = num - 1
  until num == 0
  return fib(n - 2) + fib(n - 1)
end
