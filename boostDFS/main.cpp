#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/depth_first_search.hpp>
#include <boost/range/irange.hpp>
#include <boost/graph/graphviz.hpp>
#include <boost/pending/indirect_cmp.hpp>

#include <iostream>

using namespace boost;
using namespace std;

bool compareEdges(pair<int, int> i1, pair<int, int> i2)
{
    return i1.first == i2.first ? i1.second < i2.second : i1.first < i2.first;
}

int ind;

class dfs_time_visitor:public default_dfs_visitor {
    bool is_preorder;
    int * array;
public:
    dfs_time_visitor(bool is_preorder, int * arr) {
        this->is_preorder = is_preorder;
        array = arr;
        ind = 0;
    }
    template < typename Vertex, typename Graph >
    void discover_vertex(Vertex u, const Graph & g) const
    {
        if (is_preorder) {
            array[ind] = u;
            ind++;
        }
    }
    template < typename Vertex, typename Graph >
    void finish_vertex(Vertex u, const Graph & g) const
    {
        if (!is_preorder) {
            array[ind] = u;
            ind++;
        }
    }
};

int * traverse_tree(const string& filename, int * vertices_count, bool is_pre_order) {
    typedef adjacency_list<vecS, vecS, directedS> graph_t;
    typedef graph_traits<graph_t>::vertices_size_type size_type;

    ifstream infile(filename);
    int n_vertices, n_edges;
    string tmp;
    for (int i = 0; i < 8; i++) {
        infile >> tmp;
    }

    infile >> n_vertices;
    infile >> n_edges;

    *vertices_count = n_vertices;
//    std::cout << n_vertices << " " << n_edges << std::endl;
//    cout << "\n";

    vector<pair<int, int>> edges(n_edges);
    int * root_finder = new int[n_vertices]{0};
    for (int i = 0; i < n_edges; i++) {
        int f, s;
        infile >> f >> s >> tmp;
//        std::cout << f << " " << s << "\n";
        edges[i] = {f - 1, s - 1};
        root_finder[s - 1]++;
    }
    infile.close();

    int root = 0;
    for (int i = 0; i < n_vertices; i++) {
        if (root_finder[i] == 0) {
            root = i;
            break;
        }
    }

    delete[](root_finder);

//    cout << "\n";

    int * res = new int[n_vertices]{0};

    sort(edges.begin(), edges.end(), compareEdges);
//    for (int i = 0; i < n_edges; i++) {
//        cout << edges[i].first << " " << edges[i].second << "\n";
//    }

    graph_t G(edges.begin(), edges.end(), n_vertices);

    dfs_time_visitor vis_pre_order(is_pre_order, res);

    depth_first_search(G, visitor(vis_pre_order).root_vertex(root));

    return res;
}


int main() {

    int * result_pre_order = NULL;
    int * result_post_order = NULL;
    int n_vertices;

    result_pre_order = traverse_tree("/home/adminlinux/boostDFS/test2.mtx", &n_vertices, true);
    result_post_order = traverse_tree("/home/adminlinux/boostDFS/test2.mtx", &n_vertices, false);

    cout << "Pre-order: ";
    for (int i = 0; i < n_vertices; i++) {
        cout << result_pre_order[i] << " ";
    }
    cout << endl;

    cout << "Post-order: ";
    for (int i = 0; i < n_vertices; i++) {
        cout << result_post_order[i] << " ";
    }
    cout << endl;

    delete[](result_post_order);
    delete[](result_pre_order);

    return 0;
}
