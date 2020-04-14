import numpy as np


# Returns perm matrix and amount of moved lines
def get_perm_matrix(s, pos_vector, tay, beta):
    amount_of_children = int(np.dot(pos_vector.transpose(), pos_vector))
    # step 1: move to up of s_u
    step1 = np.identity((pos_vector.shape[0]))
    tmp_beta = beta
    # in such way order of other nodes in unordered can be changed!
    for pos, val in enumerate(pos_vector):
        if val == 1:
            tmp = step1[:, pos].copy()
            step1[:, tmp_beta + 1:pos + 1] = step1[:, tmp_beta:pos]
            step1[:, tmp_beta] = tmp
            tmp_beta += 1

    # step 2: move from top of s_u to top of s_p
    step2 = np.identity((pos_vector.shape[0]))
    tmp = step2[:, beta:tmp_beta].copy()
    step2[:, tay + amount_of_children:tmp_beta] = step2[:, tay:beta]
    step2[:, tay:tay + amount_of_children] = tmp

    # summarizing perm matrix
    res = np.dot(step1, step2)
    return res, amount_of_children


def get_vector_e(size, pos):
    res = np.zeros(size)
    res[pos] = 1
    return res


def pre_order(input, root):
    v = input.shape[0]  # amount of vertices

    s = np.arange(v)
    tay = 0
    beta = 0

    # push root
    perm_matrix, alpha = get_perm_matrix(s, get_vector_e(v, root), tay, beta)
    s = np.dot(perm_matrix.transpose(), s)
    input = np.dot(perm_matrix.transpose(), input)
    input = np.dot(input, perm_matrix)
    beta += alpha

    while tay < v:
        # find children
        children = np.dot(input.transpose(), get_vector_e(v, tay))
        # pop
        tay += 1
        beta += 1 if beta < tay else 0

        # push
        perm_matrix, alpha = get_perm_matrix(s, children, tay, beta)
        s = np.dot(perm_matrix.transpose(), s)
        input = np.dot(perm_matrix.transpose(), input)
        input = np.dot(input, perm_matrix)
        beta += alpha

    return s


def post_order(input, root):
    v = input.shape[0]  # amount of vertices

    s = np.arange(v)
    tay = 0
    beta = 0

    # push root
    perm_matrix, alpha = get_perm_matrix(s, get_vector_e(v, root), tay, beta)
    s = np.dot(perm_matrix.transpose(), s)
    input = np.dot(perm_matrix.transpose(), input)
    input = np.dot(input, perm_matrix)
    beta += alpha

    while tay < v:
        # find children
        children = np.dot(input.transpose(), get_vector_e(v, tay))

        if np.sum(children[tay:]) == 0:
            # pop
            tay += 1
            beta += 1 if beta < tay else 0
        else:
            # push
            perm_matrix, alpha = get_perm_matrix(s, children, tay, beta)
            s = np.dot(perm_matrix.transpose(), s)
            input = np.dot(perm_matrix.transpose(), input)
            input = np.dot(input, perm_matrix)
            beta += alpha

    return s


if __name__ == '__main__':
    input = np.array(
        [
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 1, 0, 1],
            [1, 1, 0, 0, 0]
        ]
    )
    # indexing of nodes from zero
    root = 3

    # input = np.array(
    #     [
    #         [0, 0, 0, 0],
    #         [0, 0, 0, 0],
    #         [1, 1, 0, 0],
    #         [0, 0, 1, 0]
    #     ]
    # )
    # # indexing of nodes from zero
    # root = 3

    res = post_order(input, root)
    print(res)
