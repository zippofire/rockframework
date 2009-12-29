SELECT _obj
  FROM Email AS _obj
      INNER JOIN _obj.pessoa AS _obj_pessoa
      INNER JOIN _obj_pessoa.enderecos AS _obj_pessoa_enderecos
      INNER JOIN _obj_pessoa.telefones AS _obj_pessoa_telefones
  WHERE _obj_pessoa.id = 8