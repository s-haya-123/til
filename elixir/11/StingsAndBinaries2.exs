defmodule Anagram do
    def anagram?(word1,word2) do
        word2 
        |> Enum.all?(&(&1 in word1))
    end
end