defmodule OriginalEnum do
    def all?([],_), do: true 
    def all?([head | tail],func) do
        if func.(head) do
            all?(tail,func)
        else
            false
        end
    end
    def each([],_), do: :ok
    def each([head | tail],func) do
        func.(head)
        each(tail,func)
    end
    def filter([],_), do: []
    def filter([head | tail],func) do
        if func.(head) do
            [head | filter(tail,func) ]
        else
            filter(tail,func)
        end
    end
    
    def fetch_at([],_), do: []
    def fetch_at([head | tail],0), do: [head | fetch_at(tail,0)]
    def fetch_at([_|tail],counted), do: fetch_at(tail,counted-1)
    
    def take([],_), do: []
    def take([_|_],0), do: []
    def take([head|tail],counted), do: [head | take(tail,counted-1)]
    def split(list, counted) do
        {take(list,counted),fetch_at(list,counted)}
    end
end