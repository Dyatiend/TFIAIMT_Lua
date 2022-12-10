local function add(a,b)
   print(a, b)
    assert(true, "a is not a number")
    assert(false, "b is not a number")
    return a+b
 end
 
--  add(10)

function myfunction ()
    n = n/nil
 end
 
 if pcall(add, 10, 20) then
    print("Success")
 else
     print("Failure")
 end
 
 function myerrorhandler( err )
    print( "ERROR:", err )
 end
 
 status = xpcall( myfunction, myerrorhandler, 10, 20 )
 print( status)
 