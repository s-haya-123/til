defmodule StringCalc do
    def calculate([],value) do

    end
    def _number_digits([],value), do: {value,[]}
    def _number_digits([?\s | tail],value), do: {value,tail}
    def _number_digits([digit | tail],value) when digit in '0123456789' do
        _number_digits(tail,value*10+digit-?0)
    def calculate(str) ,do:calculate(str,0)
end