defmodule CopyOrderList do
    def add_total_amount(order,tax_rate) do
        if order[:ship_to] == :NC or order[:ship_to] == :TX do
            Enum.concat(order,[total_amount: order[:net_amount] * tax_rate[order[:ship_to]]])
        else
            order
        end
    end
    def copy(tax_rate,orders) do
        orders 
        |> Enum.map(&(add_total_amount(&1,tax_rate)))
    end
end

tax_rate = [ NC: 0.075, TX: 0.08 ]
orders = [
    [id: 123, ship_to: :NC, net_amount: 100.0],
    [id: 124, ship_to: :OK, net_amount: 35.50],
    [id: 125, ship_to: :TX, net_amount: 24.00],
    [id: 126, ship_to: :TX, net_amount: 44.80],
    [id: 127, ship_to: :NC, net_amount: 25.00],
    [id: 128, ship_to: :MA, net_amount: 10.00],
    [id: 129, ship_to: :CA, net_amount: 102.0],
    [id: 130, ship_to: :NC, net_amount: 50.00]
]