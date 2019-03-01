defmodule Print do
    def can_print(string_list) do
        string_list
        |> Enum.all?(&(&1 >= 32 and &1 <= 126))
    end
end