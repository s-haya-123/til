defmodule MyList do
    def mapsum([],_) ,do: 0
    def mapsum([head | tail],func) ,do: func.(head) + mapsum(tail,func)
    def max(list), do: _max(list,0)
    defp _max([],value), do: value 
    defp _max([head | tail],value) when head > value do _max(tail,head) end
    defp _max([_ | tail],value) do _max(tail,value) end
    def caesar([], _n), do: []
    def caesar([head | tail],n) when head + n < 122 do [head + n | caesar(tail,n)] end
    def caesar([head | tail],n)  do [ head + n - 26 | caesar(tail,n)] end
end
