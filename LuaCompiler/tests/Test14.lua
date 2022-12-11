rows = read()
cols = read()
table = {}
for i=1, rows do
  table[i] = {}
  for j=1, cols do
    table[i][j] = read()
  end
end

i = 1
repeat
  endFlag = true
  maxCol = 1
  minRow = i
  minRowNumb = table[i][1]
  for j=2, cols do
    if minRowNumb > table[i][j] then
      minRowNumb = table[i][j]
      maxCol = j
    end
  end
  maxColNumb = table[i][maxCol]
  for newI=1, rows do
    if maxColNumb < table[newI][maxCol] then 
      endFlag = false
    end
  end
  i = i + 1
until endFlag

print("-------------")
print(table[minRow][maxCol])

-- for i=1, rows do
--   for j=1, cols do
--     print(table[i][j])
--   end
-- end