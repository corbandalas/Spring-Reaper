create function
    get_balance(account_id bigint)
    returns bigint
    language plpgsql
as
$$
declare
    tx_sum_deposit  bigint;
    tx_sum_withdraw bigint;

begin
    SELECT COALESCE(sum(amount), 0)
    into tx_sum_withdraw
    from transaction
    where from_account_id = account_id;

    SELECT COALESCE(sum(amount), 0)
    into tx_sum_deposit
    from transaction
    where to_account_id = account_id;

    return tx_sum_deposit - tx_sum_withdraw;

end;
$$;