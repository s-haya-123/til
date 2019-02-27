defmodule MyList do
    def span(from,to) do
        for x <- from..to, do: x
    end
end