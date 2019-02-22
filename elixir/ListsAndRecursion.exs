defmodule MyList do
    def span(list = [head | tail],to) when head + 1 < to do
       span( [ head + 1 |   list  ],to)
    end
    def span(list = [head | tail],to) when head + 1 == to do
        [ to | list ]
    end
    def span(from,to), do: span([from],to)
end