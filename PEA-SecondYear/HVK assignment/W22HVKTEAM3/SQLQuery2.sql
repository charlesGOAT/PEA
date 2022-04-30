

declare myCursor cursor for select CustomerId from dbo.Customer 
declare @bruh int
open myCursor 

fetch next from myCursor into
@bruh
while @@FETCH_STATUS = 0
begin 
update dbo.Customer
set Password = 'password1234'
where CustomerId = @bruh
fetch next from myCursor into @bruh
end
close myCursor
deallocate myCursor