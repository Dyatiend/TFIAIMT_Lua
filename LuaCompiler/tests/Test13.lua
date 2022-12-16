d = read()

if d == "plus" then 
	function f(a)
		
		a[1] = a[1] + 1
	end
else
	function f(a, b)
		print(b)
		a[1] = a[1] - 1
	end
end

t = {2}

f(t)
print(t[1])

