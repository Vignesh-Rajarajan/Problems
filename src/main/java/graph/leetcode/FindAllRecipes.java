package graph.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/
public class FindAllRecipes {

    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        // 1. Prepare the serving table (where we put successful dishes).
        List<String> result = new ArrayList<>();

        // 2. Organize the Cookbook:
        // We map every recipe name to its page number (index) so we can quickly look up its ingredients later.
        Map<String, Integer> adjListIdx = new HashMap<>();

        // 3. The "Knowledge Base":
        // This map remembers two things:
        // - Things we definitely HAVE (Supplies) -> mapped to True.
        // - Things we figured out we CANNOT make -> mapped to False.
        Map<String, Boolean> canCook = new HashMap<>();

        // FILLING THE COOKBOOK INDEX
        for (int i = 0; i < recipes.length; i++) {
            adjListIdx.put(recipes[i], i);
        }

        // STOCKING THE PANTRY
        // Mark all raw supplies as "True" because we already have them ready to go.
        for (String supply : supplies) {
            canCook.put(supply, true);
        }

        // 4. THE GRAND TOUR:
        // Go through every single recipe in the book one by one.
        for (String recipe : recipes) {
            // Ask the Investigator (dfs function) to check if this specific recipe is possible.
            // If the Investigator says "Yes", place it on the serving table.
            if (dfs(recipe, adjListIdx, canCook, ingredients)) {
                result.add(recipe);
            }
        }

        return result;
    }

    public boolean dfs(String recipe, Map<String,Integer> adjListIdx, Map<String,Boolean> canCook, List<List<String>> ingredients){
        // CHECK 1: THE MEMORY CHECK
        // Do we already know the answer for this item?
        // - Is it a raw supply we have? (Returns True)
        // - Is it a dish we already successfully cooked earlier? (Returns True)
        // - Is it a dish we already tried to make and failed? (Returns False)
        // - Is it a dish we are CURRENTLY trying to make in a loop? (Returns False)
        if(canCook.containsKey(recipe)){
            return canCook.get(recipe);
        }

        // CHECK 2: THE EXISTENCE CHECK
        // If it's not in our Memory/Pantry (Check 1 failed), and it's NOT in the cookbook...
        // Then this item doesn't exist anywhere! We can't make it.
        if(!adjListIdx.containsKey(recipe)){
            return false;
        }

        // CHECK 3: PREVENTING INFINITE LOOPS (Crucial Step)
        // We are about to start making this recipe.
        // We temporarily mark it as "False" (Under Construction).
        // Why? If Recipe A needs Recipe B, and Recipe B needs Recipe A,
        // when B looks at A, it sees "False" and stops immediately, preventing a crash.
        canCook.put(recipe, false);

        // CHECK 4: CHECKING THE INGREDIENTS
        // Look up the ingredients for this specific recipe.
        for(String neigh: ingredients.get(adjListIdx.get(recipe))){
            // Recursively ask the Investigator about this ingredient.
            // If even ONE ingredient is impossible to get/make, the whole recipe fails.
            if(!dfs(neigh, adjListIdx, canCook, ingredients)){
                // Pass the "False" result back up the chain.
                return false;
            }
        }

        // SUCCESS!
        // If we survived the loop, it means all ingredients are available or cookable.
        // Update our Knowledge Base: "Yes, this recipe is possible."
        canCook.put(recipe, true);
        return true;
    }
}
