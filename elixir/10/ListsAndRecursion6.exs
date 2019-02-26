defmodule MyList do
    def flatten([],resultList), do: Enum.reverse(resultList)
    def flatten([head | tail],resultList) when is_list(head) do
        flatten(tail,Enum.concat(Enum.reverse(flatten(head)), resultList))
    end
    def flatten([head | tail],resultList) do
        flatten(tail,[head | resultList])
    end
    def flatten(list) do
        flatten(list,[])
    end
end