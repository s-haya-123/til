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

    
end